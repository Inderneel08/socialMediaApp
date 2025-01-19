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
        Friends friends = new Friends();

        friends.setSenderId(senderId);

        friends.setRecieverId(receiverId);

        friends.setCurrent_status(0);

        friendRepository.save(friends);
    }

}
