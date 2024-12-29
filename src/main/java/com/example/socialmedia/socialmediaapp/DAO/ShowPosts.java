package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowPosts {

    private BigInteger id;

    private String post_content;

    private BigInteger likes;

    private BigInteger dislikes;

    private BigInteger userId;

    private Timestamp created_at;

    private Timestamp updated_at;

    private String first_name;

    private String last_name;

    private String address;

    private String media_content_path;
}
