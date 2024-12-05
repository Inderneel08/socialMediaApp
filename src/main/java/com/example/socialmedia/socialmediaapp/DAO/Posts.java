package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Posts {

    @Id
    private BigInteger id;

    @Column(name = "post_content")
    private String post_content;

    @Column(name = "likes")
    private BigInteger likes;

    @Column(name = "dislikes")
    private BigInteger dislikes;

    @Column(name = "userId")
    private BigInteger userId;

}
