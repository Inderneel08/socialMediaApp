package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.Notifications;

public interface NotificationRepository extends JpaRepository<Notifications, BigInteger> {

        @Modifying
        @Query(value = "DELETE FROM notifications where notifications.postId = :postId and notifications.senderId = :senderId and notifications.recieverId = :recieverId and notifications.action = :action and notifications.seen = 0", nativeQuery = true)
        void deleteLikeNotification(@Param("postId") BigInteger postId, @Param("senderId") BigInteger senderId,
                        @Param("recieverId") BigInteger recieverId,
                        @Param("action") String action);

        @Modifying
        @Query(value = "DELETE FROM notifications where notifications.senderId = :senderId and notifications.recieverId = :recieverId and notifications.action = :action and notifications.seen = 0", nativeQuery = true)
        void deleteFriendRequestNotification(@Param("senderId") BigInteger senderId,
                        @Param("recieverId") BigInteger recieverId, @Param("action") String action);

        @Modifying
        @Query(value = "DELETE FROM notifications where notifications.senderId = :senderId and notifications.recieverId = :recieverId and notifications.action = :action and notifications.seen = 1", nativeQuery = true)
        void declineFriendRequest(@Param("senderId") BigInteger senderId,
                        @Param("recieverId") BigInteger recieverId, @Param("action") String action);

        @Query(value = "SELECT notifications.*,u.first_name,u.last_name,u.profile_photo FROM notifications LEFT JOIN users u on u.id=notifications.senderId where notifications.recieverId= :recieverId ORDER BY notifications.created_at DESC", nativeQuery = true)
        Page<Object[]> getNotifications(@Param("recieverId") BigInteger recieverId, Pageable pageable);

        @Query(value = "SELECT COUNT(*) from notifications where notifications.recieverId = :recieverId and notifications.seen=0", nativeQuery = true)
        BigInteger getCountNotifications(@Param("recieverId") BigInteger recieverId);

        @Modifying
        @Query(value = "UPDATE notifications set notifications.seen = 1 where notifications.id IN (:notifications)", nativeQuery = true)
        void setSeenStatus(@Param("notifications") List<BigInteger> notifications);

        @Modifying
        @Query(value = "UPDATE notifications set notifications.action = :action where notifications.senderId = :senderId and notifications.recieverId = :recieverId and notifications.seen=1", nativeQuery = true)
        void acceptFriendRequest(@Param("senderId") BigInteger senderId,
                        @Param("recieverId") BigInteger recieverId, @Param("action") String action);
}
