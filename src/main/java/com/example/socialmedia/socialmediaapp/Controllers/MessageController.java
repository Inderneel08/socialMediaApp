package com.example.socialmedia.socialmediaapp.Controllers;

import java.math.BigInteger;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.FriendRequestServiceLayer;
import com.example.socialmedia.socialmediaapp.Service.MessageServiceLayer;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

@RestController
public class MessageController {

    @Autowired
    private MessageServiceLayer messageServiceLayer;

    @Autowired
    private UserServices userServices;

    @Autowired
    private FriendRequestServiceLayer friendRequestServiceLayer;

    @GetMapping("/getMessagesViaLogin")
    public ResponseEntity<?> fetchMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        CustomUserDetails userDetails = (CustomUserDetails) principal;

        // messageServiceLayer.fetchMessages(userDetails.getUserId())

        return (ResponseEntity.ok().build());
    }

    @PostMapping("/establishConnection")
    public ResponseEntity<?> correctConnection(@RequestParam(value = "senderId") BigInteger senderId,
            @RequestParam(value = "recieverId") BigInteger recieverId) {

        if(friendRequestServiceLayer.checkConnection(senderId, recieverId)){
            return ResponseEntity.status(200).build();
        }

        return (ResponseEntity.badRequest().body("To message this person they need to follow you first."));
    }

    @PostMapping("/updateMessageSeen")
    public ResponseEntity<?> updateMessageSeen(@RequestParam(value = "id") BigInteger id) {
        messageServiceLayer.updateMessageSeenStatus(id);

        return (ResponseEntity.ok().build());
    }

}
