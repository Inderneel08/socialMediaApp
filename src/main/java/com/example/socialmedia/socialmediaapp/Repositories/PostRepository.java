package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.socialmedia.socialmediaapp.DAO.Posts;

public interface PostRepository extends JpaRepository<Posts, BigInteger> {

    // @Query(value = "SELECT * FROM post where post.created_at >= NOW() - INTERVAL
    // 1 DAY", nativeQuery = true)
    // List<Posts> findPostsFromLast24Hours();

    @Query(value = "SELECT p.id, p.post_content, p.likes, p.dislikes, p.userId, p.created_at, p.updated_at, u.first_name, u.last_name, u.address "
            +
            "FROM post p " +
            "LEFT JOIN users u ON p.userId = u.id", nativeQuery = true)
    Page<Object[]> findPostsFromLast24Hours(Pageable pageable);

}
