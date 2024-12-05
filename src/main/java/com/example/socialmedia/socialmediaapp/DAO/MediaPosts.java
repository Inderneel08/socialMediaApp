package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "media_posts")
public class MediaPosts {

    @Id
    private BigInteger id;

    @Column(name = "media_content_path")
    private String media_content_path;

    @Column(name = "post_id")
    private BigInteger post_id;
}
