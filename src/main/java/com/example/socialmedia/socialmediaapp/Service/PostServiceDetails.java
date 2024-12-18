package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.socialmedia.socialmediaapp.DAO.Posts;
import com.example.socialmedia.socialmediaapp.DAO.ShowPosts;
import com.example.socialmedia.socialmediaapp.Repositories.PostRepository;

@Service
public class PostServiceDetails {

    @Autowired
    private PostRepository postRepository;

    public Page<ShowPosts> getLast24HoursPosts(Pageable pageable) {

        Page<Object[]> rawresults = postRepository.findPostsFromLast24Hours(pageable);

        return (rawresults.map(result -> {
            ShowPosts post = new ShowPosts();

            post.setId(BigInteger.valueOf((Long) result[0]));

            post.setPost_content((String) result[1]);

            post.setLikes(BigInteger.valueOf((Long) result[2]));

            post.setDislikes(BigInteger.valueOf((Long) result[3]));

            post.setUserId(BigInteger.valueOf((Long) result[4]));

            post.setCreated_at((Timestamp) result[5]);

            post.setUpdated_at((Timestamp) result[6]);

            post.setFirst_name((String) result[7]);

            post.setLast_name((String) result[8]);

            post.setAddress((String) result[9]);

            return (post);
        }));
    }

    public boolean createPost(String post_content, List<String> media_content_path) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Posts posts = new Posts();

        posts.setPost_content(post_content);

        posts.setLikes(BigInteger.ZERO);

        posts.setDislikes(BigInteger.ZERO);

        posts.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        posts.setUserId(userDetails.getUserId());

        try {
            postRepository.save(posts);

            if (media_content_path != null && !media_content_path.isEmpty()) {

            }
        } catch (Exception e) {
            e.printStackTrace();

            return (false);
        }

        return (true);
    }
}
