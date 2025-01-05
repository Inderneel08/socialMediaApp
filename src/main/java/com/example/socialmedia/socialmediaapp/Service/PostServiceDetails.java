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

        Page<Object[]> rawresults = postRepository.findPostsFromLast24Hours(
                userDetails.getUserId(), pageable);

        // rawresults.forEach(result -> {
        // System.out.println("Post Content: " + result[0] + " Class: " +
        // result[0].getClass());
        // System.out.println("User ID: " + result[1] + " Class: " +
        // result[1].getClass());
        // System.out.println("Likes: " + result[2] + " Class: " +
        // result[2].getClass());
        // System.out.println("Dislikes: " + result[3] + " Class: " +
        // result[3].getClass());
        // System.out.println("Post ID: " + result[4] + " Class: " +
        // result[4].getClass());
        // System.out.println("First Name: " + result[5] + " Class: " +
        // result[5].getClass());
        // System.out.println("Last Name: " + result[6] + " Class: " +
        // result[6].getClass());
        // System.out.println("Updated At: " + result[7] + " Class: " +
        // result[7].getClass());
        // System.out.println("Address: " + result[8] + " Class: " +
        // result[8].getClass());
        // System.out.println("Media Content Path: " + result[9] + " Class: " +
        // result[9].getClass());
        // System.out.println("Created At: " + result[10] + " Class: " +
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

                post.setCreated_at((Timestamp) result[5]);

                post.setFirst_name((String) result[6]);

                post.setLast_name((String) result[7]);

                post.setUpdated_at((Timestamp) result[8]);

                post.setAddress((String) result[9]);

                post.setMedia_content_path(new ArrayList<>());

                postMap.put(postId, post);
            }

            String mediaPath = (String) result[10];

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

        Likes likes = likeRepository.findLikes(postid, userid);

        if (likes == null) {
            likes = new Likes();

            likes.setPost_id(postid);

            likes.setAction(1);

            likes.setUserid(userid);

            try {
                likeRepository.save(likes);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try {
                if (likes.getAction() == 1) {
                    likeRepository.unlikePost(postid, userid);
                } else {
                    likeRepository.likePost(postid, userid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                postRepository.increaseLikeCount(postid);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
