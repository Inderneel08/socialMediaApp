package com.example.socialmedia.socialmediaapp.Controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.socialmediaapp.DAO.Friends;
import com.example.socialmedia.socialmediaapp.DAO.MyFriends;
import com.example.socialmedia.socialmediaapp.DAO.ShowUsers;
import com.example.socialmedia.socialmediaapp.Repositories.FriendRepository;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.FriendRequestServiceLayer;
import com.example.socialmedia.socialmediaapp.Service.NotificationServiceDetails;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

@RestController
public class FriendsController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private FriendRequestServiceLayer friendRequestServiceLayer;

    @Autowired
    private NotificationServiceDetails notificationServiceDetails;

    @Autowired
    private FriendRepository friendRepository;

    @GetMapping("/exploreFriends")
    public Page<ShowUsers> explore(@RequestParam(value = "page") int page) {
        int size = 10;

        // , Sort.by("created_at").descending()

        Pageable pageable = PageRequest.of(page, size);

        return (userServices.getAllUsers(pageable));
    }

    @GetMapping("/fetchMessages")
    public Page<MyFriends> fetchMessages(@RequestParam(value = "page") int page,
            @RequestParam(value = "message") String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return (friendRequestServiceLayer.getAllFriends(userDetails.getUserId(), page, message));
    }

    @GetMapping("/getChatMessages")
    public Page<MyFriends> getChatMessages(@RequestParam(value = "page") int page) {
        return (friendRequestServiceLayer.getMessages(page));
    }

    @PostMapping("/sendFriendRequest")
    public ResponseEntity<?> sendFriendRequest(@RequestParam(value = "friendId") BigInteger id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        try {
            friendRequestServiceLayer.sendFriendRequest(userDetails.getUserId(), id);

            Friends friends = friendRepository.getFriendsDetails(userDetails.getUserId(), id);

            if (friends == null) {
                notificationServiceDetails.deleteFriendRequestNotification(userDetails.getUserId(),
                        id);
            } else {
                notificationServiceDetails.createFriendRequestNotification(userDetails.getUserId(),
                        id);
            }

        } catch (Exception e) {
            e.printStackTrace();

            return (ResponseEntity.ok("Error"));
        }

        return (ResponseEntity.ok("Success"));
    }

    @PostMapping("/accept-friend-request")
    public ResponseEntity<?> acceptFriendRequest(@RequestParam(value = "senderId") BigInteger senderId,
            @RequestParam(value = "recieverId") BigInteger recieverId) {

        friendRequestServiceLayer.updateFriendshipStatus(senderId, recieverId);

        return (ResponseEntity.ok().build());
    }

}
