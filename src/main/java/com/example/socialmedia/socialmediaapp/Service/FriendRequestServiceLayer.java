package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.socialmedia.socialmediaapp.DAO.Friends;
import com.example.socialmedia.socialmediaapp.DAO.MyFriends;
import com.example.socialmedia.socialmediaapp.Repositories.FriendRepository;
import com.example.socialmedia.socialmediaapp.Repositories.NotificationRepository;
import com.example.socialmedia.socialmediaapp.Repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import jakarta.transaction.Transactional;

@Service
public class FriendRequestServiceLayer {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public Page<MyFriends> getAllFriends(BigInteger userid, int page) {
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        Page<Object[]> rawresults = friendRepository.getMyFriends(userid, pageable);

        List<MyFriends> friends = new ArrayList<>();

        for (Object[] result : rawresults) {
            MyFriends friends2 = new MyFriends();

            friends2.setFirst_name((String) result[0]);

            friends2.setLast_name((String) result[1]);

            friends2.setProfile_photo((String) result[2]);

            friends.add(friends2);
        }

        System.out.println(friends);

        return (new PageImpl<>(friends, pageable, friends.size()));
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
