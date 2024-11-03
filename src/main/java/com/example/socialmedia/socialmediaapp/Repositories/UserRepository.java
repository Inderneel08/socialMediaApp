package com.example.socialmedia.socialmediaapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value =  "SELECT * FROM users where users.email = :email",nativeQuery = true)
    Users findEmail(@Param("email") String email);
}
