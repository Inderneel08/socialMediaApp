package com.example.socialmedia.socialmediaapp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.socialmedia.socialmediaapp.DAO.MakePost;
import com.example.socialmedia.socialmediaapp.DAO.Posts;
import com.example.socialmedia.socialmediaapp.DAO.ShowPosts;
import com.example.socialmedia.socialmediaapp.Service.PostServiceDetails;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PostController {

    @Autowired
    private PostServiceDetails postServiceDetails;

    @GetMapping("/posts/initial")
    public Page<ShowPosts> getInitialFeeds(@RequestParam(value = "page") int page) {
        int size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by("created_at").descending());

        return (postServiceDetails.getLast24HoursPosts(pageable));
    }

    @PostMapping("/postComment")
    public ModelAndView createPost(@ModelAttribute MakePost makePost, HttpServletRequest request) {
        // System.out.println(makePost.getPostContent());

        boolean createdPost = postServiceDetails.createPost(makePost.getPostContent(), null);

        if (!createdPost) {
            ModelAndView modelAndView = new ModelAndView("redirect:/home?redirected=true");

            request.getSession().setAttribute("errorMessage", "Error creating post");

            return (modelAndView);
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/home?redirected=true");

        request.getSession().setAttribute("successMessage", "Post created successfully");

        return (modelAndView);
    }

}
