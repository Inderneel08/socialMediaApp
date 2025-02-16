package com.example.socialmedia.socialmediaapp.DAO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String senderId;

    private String recieverId;

    private String message;
}
