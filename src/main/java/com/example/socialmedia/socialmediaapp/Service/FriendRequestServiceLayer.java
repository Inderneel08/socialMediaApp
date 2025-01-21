package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.socialmedia.socialmediaapp.DAO.Friends;
import com.example.socialmedia.socialmediaapp.Repositories.FriendRepository;
import jakarta.transaction.Transactional;

@Service
public class FriendRequestServiceLayer {

    @Autowired
    private FriendRepository friendRepository;

    public List<Friends> getAllFriends(BigInteger userid) {
        return (friendRepository.getMyFriends(userid));
    }

    @Transactional
    public void sendFriendRequest(BigInteger senderId, BigInteger receiverId) {
        Friends friends = friendRepository.getFriendsDetails(senderId, receiverId);

        if (friends == null) {
            friends = new Friends();

            friends.setSenderId(senderId);

            friends.setRecieverId(receiverId);

            friends.setCurrent_status(0);

            friendRepository.save(friends);
        } else {
            // In both case when the friend requests currentStatus is 0 or 1. The friend
            // request object is deleted.
            // 0 means when the request was raised for following and 1 means when 1 person
            // followed the other. In both cases we should delete the mapping.

            friendRepository.deleteAssociation(senderId, receiverId);
        }
    }

}
