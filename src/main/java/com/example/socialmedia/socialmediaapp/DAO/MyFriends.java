package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyFriends {

    private BigInteger id;

    private String first_name;

    private String last_name;

    private String profile_photo;

    private String messageSend;

    private Long typeofMessage;

    private int seen;
}
