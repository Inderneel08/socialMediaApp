package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.socialmedia.socialmediaapp.DAO.DisplayNotifications;
import com.example.socialmedia.socialmediaapp.DAO.Notifications;
import com.example.socialmedia.socialmediaapp.Repositories.NotificationRepository;
import com.example.socialmedia.socialmediaapp.Repositories.PostRepository;
import com.example.socialmedia.socialmediaapp.Repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class NotificationServiceDetails {

        @Autowired
        private NotificationRepository notificationRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PostRepository postRepository;

        @Transactional
        public void createLikeNotification(BigInteger senderId, BigInteger postId) {
                Notifications notifications = new Notifications();

                notifications.setPostId(postId);

                notifications.setSenderId(senderId);

                notifications.setRecieverId(postRepository.getpostsOnPostId(postId).getUserId());

                notifications.setAction("LIKED");

                notificationRepository.save(notifications);
        }

        @Transactional
        public void deleteLikeNotification(BigInteger postId) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                notificationRepository.deleteLikeNotification(postId, userDetails.getUserId(),
                                postRepository.getpostsOnPostId(postId).getUserId(), "LIKED");
        }

        @Transactional
        public void createFriendRequestNotification(BigInteger senderId, BigInteger recieverId) {

                Notifications notifications = new Notifications();

                notifications.setSenderId(senderId);

                notifications.setRecieverId(recieverId);

                if (userRepository.findByUserId(recieverId).getProfile_type() == 1) {
                        notifications.setAction("REQUESTED");
                } else {
                        notifications.setAction("FOLLOWING");
                }

                notificationRepository.save(notifications);
        }

        @Transactional
        public void deleteFriendRequestNotification(BigInteger senderId, BigInteger recieverId) {

                notificationRepository.deleteFriendRequestNotification(senderId, recieverId, "REQUESTED");
        }

        @Transactional
        public void declineFriendRequest(BigInteger senderId, BigInteger recieverId)
        {
                notificationRepository.declineFriendRequest(senderId, recieverId, "REQUESTED");
        }

        // Page<Notifications>
        public Page<DisplayNotifications> getNotifications(Pageable pageable) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                Page<Object[]> rawresults = notificationRepository.getNotifications(
                                userDetails.getUserId(), pageable);

                // System.out.println(rawresults.get);

                // System.out.println(rawresults.getSize());

                List<DisplayNotifications> notifications = new ArrayList<>();

                for (Object[] result : rawresults) {
                        DisplayNotifications notifications2 = new DisplayNotifications();

                        notifications2.setId(BigInteger.valueOf((Long) result[0]));

                        notifications2.setSenderId(BigInteger.valueOf((Long) result[1]));

                        notifications2.setAction((String) result[2]);

                        notifications2.setRecieverId(BigInteger.valueOf((Long) result[3]));

                        if ((Long) result[4] != null) {
                                notifications2.setPostId(BigInteger.valueOf((Long) result[4]));
                        }

                        notifications2.setSeen((int) result[5]);

                        notifications2.setCreated_at((Timestamp) result[6]);

                        notifications2.setFirst_name((String) result[7]);

                        notifications2.setLast_name((String) result[8]);

                        notifications2.setImage((String) result[9]);

                        // System.out.println(result[0] + " " +
                        // result[0].getClass());
                        // System.out.println(result[1] + " " +
                        // result[1].getClass());
                        // System.out.println(result[2] + " " +
                        // result[2].getClass());
                        // System.out.println(result[3] + " " +
                        // result[3].getClass());
                        // System.out.println(result[4] + " " +
                        // result[4].getClass());
                        // System.out.println("Liked:->" + result[5] + " " +
                        // result[5].getClass());
                        // System.out.println(result[6] + " " +
                        // result[6].getClass());

                        notifications.add(notifications2);
                }

                return (new PageImpl<>(notifications, pageable, notifications.size()));
        }

        @Transactional
        public void setSeenStatus(List<BigInteger> notifications) {
                notificationRepository.setSeenStatus(notifications);
        }

}
