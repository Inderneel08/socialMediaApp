package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.socialmedia.socialmediaapp.DAO.StoreMessage;

public interface MessageRepository extends JpaRepository<StoreMessage, BigInteger> {
}
