package com.example.socialmedia.socialmediaapp.Controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.example.socialmedia.socialmediaapp.DAO.ChatMessage;
import com.example.socialmedia.socialmediaapp.Service.ChatServiceLayer;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatServiceLayer chatServiceLayer;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage/{chatRoomId}")
    public void sendMessage(@DestinationVariable String chatRoomId, ChatMessage chatMessage) {

        chatServiceLayer.createChatMessage(chatMessage.getSenderId(), chatMessage.getRecieverId(),
                chatMessage.getMessage());

        messagingTemplate.convertAndSend("/private/" + chatRoomId, chatMessage);
    }

}
