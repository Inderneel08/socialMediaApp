package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "friends")
public class Friends {

    @Id
    private BigInteger id;

    @Column(name = "senderId")
    private BigInteger senderId;

    @Column(name = "recieverId")
    private BigInteger recieverId;

    @Column(name = "accepted")
    private int accepted;

    @Column(name = "current_status")
    private int current_status;
}
