package com.example.socialmedia.socialmediaapp.Controllers;

import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.socialmedia.socialmediaapp.DAO.DisplayNotifications;
import com.example.socialmedia.socialmediaapp.Service.NotificationServiceDetails;

@RestController
public class NotificationController {

    @Autowired
    private NotificationServiceDetails notificationServiceDetails;

    // Page<Notifications>
    @GetMapping("/all/notifications")
    public Page<DisplayNotifications> getNotifications(@RequestParam(value = "page") int page) {
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        return (notificationServiceDetails.getNotifications(pageable));
    }

    @PostMapping("/changeSeenStatus")
    public ResponseEntity<?> bulkChangeSeenStatus(
            @RequestParam(value = "indices", required = false) List<BigInteger> notificationIndices) {

        if (notificationIndices != null) {
            try {

                // System.out.println(notificationIndices);

                // for (BigInteger notificationIndex : notificationIndices) {
                // System.out.println(notificationIndex);
                // }

                notificationServiceDetails.setSeenStatus(notificationIndices);
            } catch (Exception e) {
                e.printStackTrace();

                return (ResponseEntity.badRequest().build());
            }
        }

        return (ResponseEntity.ok().build());
    }

    @PostMapping("/delete-notification")
    public ResponseEntity<?> deleteNotification(@RequestParam(value = "senderId") BigInteger senderId,
            @RequestParam(value = "recieverId") BigInteger recieverId) {

        try {
            notificationServiceDetails.declineFriendRequest(senderId, recieverId);
        } catch (Exception e) {
            e.printStackTrace();

            return (ResponseEntity.badRequest().build());
        }

        return (ResponseEntity.ok().build());
    }

}
