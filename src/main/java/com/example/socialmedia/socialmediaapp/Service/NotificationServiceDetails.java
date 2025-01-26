package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.DAO.Notifications;
import com.example.socialmedia.socialmediaapp.Repositories.NotificationRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationServiceDetails {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createLikeNotification(BigInteger postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Notifications notifications = new Notifications();

        notifications.setPostId(postId);

        notifications.setSenderId(userDetails.getUserId());

        notifications.setAction("LIKED");

        notificationRepository.save(notifications);
    }

    @Transactional
    public void deleteLikeNotification(BigInteger postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        notificationRepository.deleteLikeNotification(postId, userDetails.getUserId(), "LIKED");
    }

    public void createFriendRequestNotification(BigInteger recieverId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Notifications notifications = new Notifications();

        notifications.setSenderId(userDetails.getUserId());

        notifications.setRecieverId(recieverId);

        notifications.setAction("REQUESTED");

        notificationRepository.save(notifications);
    }

    @Transactional
    public void deleteFriendRequestNotification(BigInteger recieverId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        notificationRepository.deleteFriendRequestNotification(userDetails.getUserId(), recieverId, "REQUESTED");
    }

}
