package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.DAO.ChatMessage;
import com.example.socialmedia.socialmediaapp.DAO.StoreMessage;
import com.example.socialmedia.socialmediaapp.Repositories.MessageRepository;

@Service
public class ChatServiceLayer {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private FriendRequestServiceLayer friendRequestServiceLayer;

    public BigInteger createChatMessage(String senderId, String recieverId, String message) {
        StoreMessage storeMessage = new StoreMessage();

        storeMessage.setSenderId(BigInteger.valueOf(Long.valueOf(senderId)));

        storeMessage.setRecieverId(BigInteger.valueOf(Long.valueOf(recieverId)));

        storeMessage.setMessageSend(message);

        StoreMessage savedMessage = messageRepository.save(storeMessage);

        return (savedMessage.getId());
    }

}
