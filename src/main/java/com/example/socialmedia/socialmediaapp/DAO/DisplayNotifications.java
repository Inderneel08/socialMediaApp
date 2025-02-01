package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisplayNotifications {

    private BigInteger id;

    private BigInteger senderId;

    private String action;

    private BigInteger recieverId;

    private BigInteger postId;

    private int seen;

    private Timestamp created_at;

    private String first_name;

    private String last_name;

    private String image;
}
