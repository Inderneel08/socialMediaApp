package com.example.socialmedia.socialmediaapp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.socialmediaapp.DAO.Posts;
import com.example.socialmedia.socialmediaapp.Service.PostServiceDetails;

@RestController
public class PostController {

    @Autowired
    private PostServiceDetails postServiceDetails;

    @GetMapping("/posts/initial")
    public List<Posts> getInitialFeeds() {
        return(postServiceDetails.getLast24HoursPosts());
    }

}
