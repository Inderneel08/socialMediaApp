package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.MessageServiceLayer;

@RestController
public class MessageController {

    @Autowired
    private MessageServiceLayer messageServiceLayer;

    @GetMapping("/getMessagesViaLogin")
    public void fetchMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        CustomUserDetails userDetails = (CustomUserDetails) principal;

        messageServiceLayer.fetchMessages(userDetails.getUserId());
    }

}
