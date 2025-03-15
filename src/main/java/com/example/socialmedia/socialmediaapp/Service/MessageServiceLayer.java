package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.socialmedia.socialmediaapp.DAO.MessageSenderDetails;
import com.example.socialmedia.socialmediaapp.DAO.MyFriends;
import com.example.socialmedia.socialmediaapp.Repositories.MessageRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageServiceLayer {

    @Autowired
    private MessageRepository messageRepository;

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

    @Transactional
    public void updateBulkSeenStatusOnRecieverId(BigInteger userid){
        messageRepository.updateBulkSeenStatusOnRecieverId(userid);
    }

    public void fetchMessages(BigInteger userid) {
        // messages.messageSend,users.id,users.first_name,users.last_name
        List<Object[]> messageObject = messageRepository.fetchMessagesViaLogin(userid);

        List<MyFriends> myFriends;

        for (Object[] obj : messageObject) {
            System.out.println("Message: " + obj[0] + ", ID: " + obj[1] +
                    ", First Name: " + obj[2] + ", Last Name: " + obj[3]);

            MyFriends friends = new MyFriends();

            // myFriends.Badd()
        }
    }

    @Transactional
    public void updateMessageSeenStatus(BigInteger id)
    {
        messageRepository.updateSeenStatus(id);
    }

}
