package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.socialmedia.socialmediaapp.DAO.ChatMessage;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage/{chatRoomId}")
    public void sendMessage(@DestinationVariable String chatRoomId, ChatMessage chatMessage) {

        System.out.println(chatMessage.getMessage());

        messagingTemplate.convertAndSend("/private/" + chatRoomId, chatMessage);
    }

}
