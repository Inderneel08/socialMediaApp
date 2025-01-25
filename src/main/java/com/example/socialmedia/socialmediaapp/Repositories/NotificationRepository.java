package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.socialmedia.socialmediaapp.DAO.Notifications;

public interface NotificationRepository extends JpaRepository<Notifications, BigInteger> {

}
