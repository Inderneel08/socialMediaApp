package com.example.socialmedia.socialmediaapp.Service;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.socialmedia.socialmediaapp.DAO.Posts;
import com.example.socialmedia.socialmediaapp.Repositories.PostRepository;

@Service
public class PostServiceDetails {

    @Autowired
    private PostRepository postRepository;

    public List<Posts> getLast24HoursPosts() {
        return (postRepository.findPostsFromLast24Hours());
    }

    public void createPost(String post_content, List<String> media_content_path) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Posts posts = new Posts();

        posts.setPost_content(post_content);

        posts.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        posts.setUserId(userDetails.getUserId());

        try {
            postRepository.save(posts);

            if (!media_content_path.isEmpty()) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
