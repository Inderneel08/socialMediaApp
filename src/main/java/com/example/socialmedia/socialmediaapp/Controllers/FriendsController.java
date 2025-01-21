package com.example.socialmedia.socialmediaapp.Controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.socialmedia.socialmediaapp.DAO.ShowUsers;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.FriendRequestServiceLayer;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

@RestController
public class FriendsController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private FriendRequestServiceLayer friendRequestServiceLayer;

    @GetMapping("/exploreFriends")
    public Page<ShowUsers> explore(@RequestParam(value = "page") int page) {
        int size = 10;

        // , Sort.by("created_at").descending()

        Pageable pageable = PageRequest.of(page, size);

        return (userServices.getAllUsers(pageable));
    }

    @PostMapping("/sendFriendRequest")
    public ResponseEntity<?> sendFriendRequest(@RequestParam(value = "friendId") BigInteger id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        try {
            friendRequestServiceLayer.sendFriendRequest(userDetails.getUserId(), id);

        } catch (Exception e) {
            e.printStackTrace();

            return (ResponseEntity.ok("Error"));
        }

        return (ResponseEntity.ok("Success"));
    }

}
