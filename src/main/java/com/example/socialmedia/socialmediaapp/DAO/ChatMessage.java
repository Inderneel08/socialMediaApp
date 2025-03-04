package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private BigInteger id;

    private String senderId;

    private String recieverId;

    private String message;
}
