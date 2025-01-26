package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.Notifications;

public interface NotificationRepository extends JpaRepository<Notifications, BigInteger> {

    @Modifying
    @Query(value = "DELETE FROM notifications where notifications.postId = :postId and notifications.senderId = :senderId and notifications.action = :action and notifications.seen = 0")
    void deleteLikeNotification(@Param("postId") BigInteger postId, @Param("senderId") BigInteger senderId,
            @Param("action") String action);

    @Modifying
    @Query(value = "DELETE FROM notifications where notifications.senderId = :senderId and notifications.recieverId = :recieverId and notifications.action = :action and notifications.seen = 0")
    void deleteFriendRequestNotification(@Param("senderId") BigInteger senderId,
            @Param("recieverId") BigInteger recieverId, @Param("action") String action);
}
