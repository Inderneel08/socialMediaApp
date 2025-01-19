package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.socialmedia.socialmediaapp.DAO.Likes;
import com.example.socialmedia.socialmediaapp.DAO.Posts;
import com.example.socialmedia.socialmediaapp.DAO.ShowPosts;
import com.example.socialmedia.socialmediaapp.Repositories.LikeRepository;
import com.example.socialmedia.socialmediaapp.Repositories.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class PostServiceDetails {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UploadServiceDetails uploadServiceDetails;

    public Page<ShowPosts> getLast24HoursPosts(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        System.out.println("UserId : ->"+ userDetails.getUserId());

        Page<Object[]> rawresults = postRepository.findPostsFromLast24Hours(
                userDetails.getUserId(), pageable);

        // rawresults.forEach(result -> {
        // System.out.println("-----------------------------");

        // System.out.println(result[0] + " " +
        // result[0].getClass());
        // System.out.println(result[1] + " " +
        // result[1].getClass());
        // System.out.println(result[2] + " " +
        // result[2].getClass());
        // System.out.println(result[3] + " " +
        // result[3].getClass());
        // System.out.println(result[4] + " " +
        // result[4].getClass());
        // System.out.println("Liked:->"+ result[5] + " " +
        // result[5].getClass());
        // System.out.println(result[6] + " " +
        // result[6].getClass());
        // System.out.println(result[7] + " " +
        // result[7].getClass());
        // System.out.println(result[8] + " " +
        // result[8].getClass());
        // System.out.println(result[9] + " " +
        // result[9].getClass());
        // System.out.println(result[10] + " " +
        // result[10].getClass());
        // });

        Map<BigInteger, ShowPosts> postMap = new LinkedHashMap<>();

        for (Object[] result : rawresults) {
            BigInteger postId = BigInteger.valueOf((Long) result[4]);

            ShowPosts post = postMap.get(postId);

            if (post == null) {
                post = new ShowPosts();

                post.setPost_content((String) result[0]);

                post.setUserId(BigInteger.valueOf((Long) result[1]));

                post.setLikes(BigInteger.valueOf((Long) result[2]));

                post.setDislikes(BigInteger.valueOf((Long) result[3]));

                post.setId(BigInteger.valueOf((Long) result[4]));

                if ((int) result[5] == 0) {
                    post.setLiked(false);
                } else {
                    post.setLiked(true);
                }

                post.setCreated_at((Timestamp) result[6]);

                post.setFirst_name((String) result[7]);

                post.setLast_name((String) result[8]);

                post.setUpdated_at((Timestamp) result[9]);

                post.setAddress((String) result[10]);

                post.setMedia_content_path(new ArrayList<>());

                postMap.put(postId, post);
            }

            String mediaPath = (String) result[11];

            if (mediaPath != null) {
                post.getMedia_content_path().add(mediaPath);
            }
        }

        List<ShowPosts> groupedPosts = new ArrayList<>(postMap.values());

        return (new PageImpl<>(groupedPosts, pageable, groupedPosts.size()));

        // return (rawresults.map(result -> {
        // ShowPosts post = new ShowPosts();

        // post.setPost_content((String) result[0]);

        // post.setUserId(BigInteger.valueOf((Long) result[1]));

        // post.setLikes(BigInteger.valueOf((Long) result[2]));

        // post.setDislikes(BigInteger.valueOf((Long) result[3]));

        // post.setId(BigInteger.valueOf((Long) result[4]));

        // post.setCreated_at((Timestamp) result[5]);

        // post.setFirst_name((String) result[6]);

        // post.setLast_name((String) result[7]);

        // post.setUpdated_at((Timestamp) result[8]);

        // post.setAddress((String) result[9]);

        // post.setMedia_content_path((String) result[10]);

        // return (post);
        // }));
    }

    @Transactional
    public void likePost(BigInteger postid, BigInteger userid) {

        Likes likes = new Likes();

        likes.setPost_id(postid);

        likes.setAction(1);

        likes.setUserid(userid);

        try {
            likeRepository.save(likes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            postRepository.increaseLikeCount(postid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateLikeStatus(BigInteger postid, BigInteger userid) {
        likeRepository.likePost(postid, userid);

        try {
            postRepository.increaseLikeCount(postid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateUnlikeStatus(BigInteger postid, BigInteger userid) {
        likeRepository.unlikePost(postid, userid);

        try {
            postRepository.decreaseLikeCount(postid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createPost(String post_content, List<MultipartFile> postImages) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Posts posts = new Posts();

        posts.setPost_content(post_content);

        posts.setLikes(BigInteger.ZERO);

        posts.setDislikes(BigInteger.ZERO);

        posts.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        posts.setUserId(userDetails.getUserId());

        String uploadDirectory = "src/main/resources/static/images/postPictures";

        try {
            Posts savedPost = postRepository.save(posts);

            BigInteger postId = savedPost.getId();

            if (postImages != null && !postImages.isEmpty()) {
                for (MultipartFile file : postImages) {
                    try {
                        String filename = UUID.randomUUID().toString() + "_" + LocalDate.now().toString() + "_"
                                + file.getOriginalFilename();

                        uploadServiceDetails.upload(file, filename, uploadDirectory, "post-photo", postId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            return (false);
        }

        return (true);
    }
}
