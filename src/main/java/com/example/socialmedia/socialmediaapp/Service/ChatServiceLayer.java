package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.DAO.MessageSenderDetails;
import com.example.socialmedia.socialmediaapp.DAO.MyFriends;
import com.example.socialmedia.socialmediaapp.DAO.StoreMessage;
import com.example.socialmedia.socialmediaapp.Repositories.MessageRepository;

@Service
public class ChatServiceLayer {

    @Autowired
    private MessageRepository messageRepository;

    public void createChatMessage(String senderId, String recieverId, String message) {
        StoreMessage storeMessage = new StoreMessage();

        storeMessage.setSenderId(BigInteger.valueOf(Long.valueOf(senderId)));

        storeMessage.setRecieverId(BigInteger.valueOf(Long.valueOf(recieverId)));

        storeMessage.setMessageSend(message);

        messageRepository.save(storeMessage);
    }

    private MessageSenderDetails mapMessageDetails(Object[] result) {
        MessageSenderDetails messageSenderDetails = new MessageSenderDetails();

        messageSenderDetails.setSenderId(BigInteger.valueOf((Long) result[0]));

        return messageSenderDetails;
    }

    public int computeMessageSize(BigInteger userid) {
        List<Object[]> rawresults = messageRepository.findMessagesById(userid);

        List<MessageSenderDetails> messageSenderDetails = new ArrayList<>();

        rawresults.forEach(result -> messageSenderDetails.add(mapMessageDetails(result)));

        return (messageSenderDetails.size());
    }

}
