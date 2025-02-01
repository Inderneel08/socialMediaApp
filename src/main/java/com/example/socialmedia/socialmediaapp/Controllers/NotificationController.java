package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.socialmediaapp.DAO.Notifications;
import com.example.socialmedia.socialmediaapp.Service.NotificationServiceDetails;

@RestController
public class NotificationController {

    @Autowired
    private NotificationServiceDetails notificationServiceDetails;

    // Page<Notifications>
    @GetMapping("/all/notifications")
    public Page<Notifications> getNotifications(@RequestParam(value = "page") int page) {
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        return(notificationServiceDetails.getNotifications(pageable));
    }

}
