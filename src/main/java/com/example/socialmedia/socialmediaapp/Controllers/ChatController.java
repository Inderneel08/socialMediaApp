package com.example.socialmedia.socialmediaapp.Controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.example.socialmedia.socialmediaapp.DAO.ChatMessage;
import com.example.socialmedia.socialmediaapp.Service.ChatServiceLayer;
import com.example.socialmedia.socialmediaapp.Service.FriendRequestServiceLayer;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatServiceLayer chatServiceLayer;

    @Autowired
    private FriendRequestServiceLayer friendRequestServiceLayer;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage/{chatRoomId}")
    public void sendMessage(@DestinationVariable String chatRoomId, ChatMessage chatMessage) {

        if (friendRequestServiceLayer.checkConnection(BigInteger.valueOf(Long.valueOf(
                chatMessage.getSenderId())), BigInteger.valueOf(Long.valueOf(chatMessage.getRecieverId())))) {
            chatMessage.setId(
                    chatServiceLayer.createChatMessage(chatMessage.getSenderId(), chatMessage.getRecieverId(),
                            chatMessage.getMessage()));

            messagingTemplate.convertAndSend("/private/" + chatRoomId, chatMessage);
        }
    }

}
