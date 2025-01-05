package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.socialmedia.socialmediaapp.DAO.ShowUsers;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

@RestController
public class FriendsController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/exploreFriends")
    public Page<ShowUsers> explore(@RequestParam(value = "page") int page)
    {
        int size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by("created_at").descending());

        return(userServices.getAllUsers(pageable));
    }

}
