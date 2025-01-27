package com.example.socialmedia.socialmediaapp.Controllers;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.socialmedia.socialmediaapp.DAO.Likes;
import com.example.socialmedia.socialmediaapp.DAO.MakePost;
import com.example.socialmedia.socialmediaapp.DAO.ShowPosts;
import com.example.socialmedia.socialmediaapp.Repositories.LikeRepository;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.NotificationServiceDetails;
import com.example.socialmedia.socialmediaapp.Service.PostServiceDetails;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PostController {

    @Autowired
    private PostServiceDetails postServiceDetails;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private NotificationServiceDetails notificationServiceDetails;

    @GetMapping("/posts/initial")
    public Page<ShowPosts> getInitialFeeds(@RequestParam(value = "page") int page) {
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        return (postServiceDetails.getLast24HoursPosts(pageable));
    }

    @PostMapping("/perform-like-unlike")
    public ResponseEntity<?> like_unlike_post(@RequestParam(value = "id") BigInteger id) {
        System.out.println("Received ID as BigInteger: " + id); // Debugging

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Likes likes = likeRepository.findLikes(id, userDetails.getUserId());

        if (likes != null) {
            if (likes.getAction() == 0) {
                postServiceDetails.updateLikeStatus(id, userDetails.getUserId());

                notificationServiceDetails.createLikeNotification(userDetails.getUserId(),id);

                return (ResponseEntity.ok("Success"));
            } else {
                postServiceDetails.updateUnlikeStatus(id, userDetails.getUserId());

                notificationServiceDetails.deleteLikeNotification(id);

                return (ResponseEntity.ok("Unlike"));
            }
        } else {
            postServiceDetails.likePost(id, userDetails.getUserId());

            // Create a like notification.
            notificationServiceDetails.createLikeNotification(userDetails.getUserId(),id);
        }

        return (ResponseEntity.ok("Success"));
    }

    @PostMapping("/postComment")
    public ModelAndView createPost(@ModelAttribute MakePost makePost, HttpServletRequest request) {
        // System.out.println(makePost.getPostContent());
        List<MultipartFile> postImages = makePost.getPostImages();

        boolean createdPost = postServiceDetails.createPost(makePost.getPostContent(), postImages);

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
