package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.socialmedia.socialmediaapp.DAO.Posts;

public interface PostRepository extends JpaRepository<Posts, BigInteger> {

    @Query(value = "SELECT * FROM post where post.created_at >= NOW() - INTERVAL 1 DAY", nativeQuery = true)
    List<Posts> findPostsFromLast24Hours();
}
