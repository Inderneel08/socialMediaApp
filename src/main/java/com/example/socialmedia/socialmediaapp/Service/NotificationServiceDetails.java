package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.DAO.Notifications;
import com.example.socialmedia.socialmediaapp.Repositories.NotificationRepository;
import com.example.socialmedia.socialmediaapp.Repositories.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationServiceDetails {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void createLikeNotification(BigInteger senderId,BigInteger postId) {
        Notifications notifications = new Notifications();

        notifications.setPostId(postId);

        notifications.setSenderId(senderId);

        notifications.setRecieverId(postRepository.getpostsOnPostId(postId).getUserId());

        System.out.println(1);

        notifications.setAction("LIKED");

        notificationRepository.save(notifications);
    }

    @Transactional
    public void deleteLikeNotification(BigInteger postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        notificationRepository.deleteLikeNotification(postId, userDetails.getUserId(),postRepository.getpostsOnPostId(postId).getUserId(), "LIKED");
    }

    @Transactional
    public void createFriendRequestNotification(BigInteger senderId,BigInteger recieverId) {

        Notifications notifications = new Notifications();

        notifications.setSenderId(senderId);

        notifications.setRecieverId(recieverId);

        notifications.setAction("REQUESTED");

        notificationRepository.save(notifications);
    }

    @Transactional
    public void deleteFriendRequestNotification(BigInteger senderId,BigInteger recieverId) {

        notificationRepository.deleteFriendRequestNotification(senderId, recieverId, "REQUESTED");
    }

}
