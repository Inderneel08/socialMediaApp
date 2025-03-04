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
import com.example.socialmedia.socialmediaapp.Service.MessageServiceLayer;

@RestController
public class MessageController {

    @Autowired
    private MessageServiceLayer messageServiceLayer;

    @GetMapping("/getMessagesViaLogin")
    public ResponseEntity<?> fetchMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        CustomUserDetails userDetails = (CustomUserDetails) principal;

        // messageServiceLayer.fetchMessages(userDetails.getUserId())

        return(ResponseEntity.ok().build());
    }


    @PostMapping("/updateMessageSeen")
    public ResponseEntity<?> updateMessageSeen(@RequestParam(value = "id") BigInteger id)
    {
        messageServiceLayer.updateMessageSeenStatus(id);

        return(ResponseEntity.ok().build());
    }

}
