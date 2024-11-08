package com.example.socialmedia.socialmediaapp.DAO;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {

    @Id
    private int id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "ip_address")
    private String ip_address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp created_at;


    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "is_verified")
    private int is_verified;

    @Column(name = "email_verificationHash")
    private String email_verificationHash;

}
