package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// Likes are done on posts. Friend requests are made by one user to another.

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "senderId")
    private BigInteger senderId;

    @Column(name = "action")
    private String action;

    @Column(name = "recieverId")
    private BigInteger recieverId;

    @Column(name = "postId")
    private BigInteger postId;

    @Column(name = "seen")
    private int seen;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp created_at;
}
