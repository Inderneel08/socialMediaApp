package com.example.socialmedia.socialmediaapp.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.DAO.Posts;
import com.example.socialmedia.socialmediaapp.Repositories.PostRepository;

@Service
public class PostServiceDetails {

    @Autowired
    private PostRepository postRepository;

    public List<Posts> getLast24HoursPosts()
    {
        return(postRepository.findPostsFromLast24Hours());
    }
}
