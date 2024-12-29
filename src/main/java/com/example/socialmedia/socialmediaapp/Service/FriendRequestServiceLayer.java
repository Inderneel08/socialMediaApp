package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.DAO.Friends;
import com.example.socialmedia.socialmediaapp.Repositories.FriendRepository;

@Service
public class FriendRequestServiceLayer {

    @Autowired
    private FriendRepository friendRepository;

    public List<Friends> getAllFriends(BigInteger userid)
    {
        return(friendRepository.getMyFriends(userid));
    }

}
