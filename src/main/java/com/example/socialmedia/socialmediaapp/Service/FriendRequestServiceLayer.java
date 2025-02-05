package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.socialmedia.socialmediaapp.DAO.Friends;
import com.example.socialmedia.socialmediaapp.DAO.MyFriends;
import com.example.socialmedia.socialmediaapp.Repositories.FriendRepository;
import com.example.socialmedia.socialmediaapp.Repositories.NotificationRepository;
import com.example.socialmedia.socialmediaapp.Repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FriendRequestServiceLayer {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public List<MyFriends> getAllFriends(BigInteger userid) {
        return (friendRepository.getMyFriends(userid));
    }

    @Transactional
    public void sendFriendRequest(BigInteger senderId, BigInteger receiverId) {
        Friends friends = friendRepository.getFriendsDetails(senderId, receiverId);

        if (friends == null) {
            friends = new Friends();

            friends.setSenderId(senderId);

            friends.setRecieverId(receiverId);

            if (userRepository.findByUserId(receiverId).getProfile_type() == 1) {
                friends.setCurrent_status(0);
            } else {
                friends.setCurrent_status(1);
            }

            friendRepository.save(friends);
        } else {
            // In both case when the friend requests currentStatus is 0 or 1. The friend
            // request object is deleted.
            // 0 means when the request was raised for following and 1 means when 1 person
            // followed the other. In both cases we should delete the mapping.

            friendRepository.deleteAssociation(senderId, receiverId);
        }
    }

    @Transactional
    public void updateFriendshipStatus(BigInteger senderId, BigInteger receiverId) {
        try {
            friendRepository.addFriendship(senderId, receiverId);

            notificationRepository.acceptFriendRequest(senderId, receiverId, "FOLLOWING");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
