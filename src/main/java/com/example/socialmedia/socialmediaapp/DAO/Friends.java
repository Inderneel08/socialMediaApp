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

@Entity
@Table(name = "friends")
@Getter
@Setter
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "senderId")
    private BigInteger senderId;

    @Column(name = "recieverId")
    private BigInteger recieverId;

    @Column(name = "current_status")
    private int current_status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp created_at;
}
