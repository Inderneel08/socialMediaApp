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

    @Autowired
    private UserServices userServices;

    private MyFriends mapToMyFriends(Object[] result) {
        MyFriends friend = new MyFriends();

        friend.setId(BigInteger.valueOf((Long) result[0]));
        friend.setFirst_name((String) result[1]);
        friend.setLast_name((String) result[2]);
        friend.setProfile_photo((String) result[3]);

        return friend;
    }

    private MyFriends mapToMyMessages(Object[] result, List<MyFriends> friends) {
        MyFriends friend = new MyFriends();

        System.out.println("Hello1");
        friend.setId(BigInteger.valueOf((Long) result[0]));
        System.out.println("Hello2");
        friend.setFirst_name((String) result[1]);
        System.out.println("Hello3");
        friend.setLast_name((String) result[2]);
        System.out.println("Hello3");
        friend.setProfile_photo((String) result[3]);
        System.out.println("Hello4");
        friend.setMessageSend((String) result[4]);
        System.out.println("Hello5");
        friend.setTypeofMessage((Long) result[5]);
        System.out.println("Hello6");
        friend.setSeen((int) result[6]);
        System.out.println("Hello7");

        for (MyFriends myFriends : friends) {
            if (myFriends.getId() == friend.getId()) {
                return null;
            }
        }

        return (friend);
    }

    public Page<MyFriends> getAllFriends(BigInteger userid, int page, String message) {
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        Page<Object[]> finalResults = friendRepository.getUsersList(userid, pageable, message);

        List<MyFriends> friends = new ArrayList<>();

        finalResults.getContent().forEach(result -> friends.add(mapToMyFriends(result)));

        long totalElements = finalResults.getTotalElements();

        return (new PageImpl<>(friends, pageable, totalElements));
    }

    public Page<MyFriends> getMessages(BigInteger userid,int page) {
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        Page<Object[]> finalResults = friendRepository.getMyMessages(userid,pageable);

        List<MyFriends> friends = new ArrayList<>();

        finalResults.getContent().forEach(result -> {
            MyFriends friend = mapToMyMessages(result, friends);
            if (friend != null) {
                friends.add(friend);
            }
        });

        long totalElements = finalResults.getTotalElements();

        return (new PageImpl<>(friends, pageable, totalElements));
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

    public boolean checkConnection(BigInteger senderId, BigInteger recieverId) {
        boolean doYouFollow = checkFollow(senderId, recieverId);

        boolean doTheyFollowYou = checkFollow(recieverId, senderId);

        int profileTypeReciever = userServices.typeOfProfile(recieverId);

        if (profileTypeReciever == 0) {
            return true;
        } else {
            if ((doYouFollow && doTheyFollowYou)) {
                return true;
            } else {
                if (doTheyFollowYou) {
                    return true;
                }
            }
        }

        return (false);
    }

    public boolean checkFollow(BigInteger senderId, BigInteger recieverId) {
        Friends friends = friendRepository.getFriendsRequestStatus(senderId, recieverId);

        if (friends == null) {
            return (false);
        }

        if (friends.getCurrent_status() == 0) {
            return (false);
        }

        return (true);
    }

}
