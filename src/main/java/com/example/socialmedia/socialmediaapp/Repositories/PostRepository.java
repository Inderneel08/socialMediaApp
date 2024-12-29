package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.Posts;

public interface PostRepository extends JpaRepository<Posts, BigInteger> {

    // @Query(value = "SELECT * FROM post where post.created_at >= NOW() - INTERVAL
    // 1 DAY", nativeQuery = true)
    // List<Posts> findPostsFromLast24Hours();

    // @Query(value = "SELECT p.id, p.post_content, p.likes, p.dislikes, p.userId,
    // p.created_at, p.updated_at, u.first_name, u.last_name, u.address "
    // +
    // "FROM post p " +
    // "LEFT JOIN users u ON p.userId = u.id", nativeQuery = true)

    // @Query(value = "SELECT p.*,m.media_content_path,m.created_at FROM (SELECT
    // post.*,u.first_name,u.last_name,u.address FROM post LEFT JOIN users AS u ON
    // post.userId=u.id) AS p LEFT JOIN media_posts AS m ON m.post_id = p.id where
    // p.userid = :userid", nativeQuery = true)
    // Page<Object[]> findPostsFromLast24Hours(Pageable pageable);

    // @Query(value = "SELECT
    // p.post_content,p.userid,p.likes,p.dislikes,p.id,p.first_name,p.last_name,p.updated_at,p.address,m.media_content_path,m.created_at
    // FROM (SELECT post.*,u.first_name,u.last_name,u.address FROM post LEFT JOIN
    // users as u ON post.userId = u.id) AS p LEFT JOIN media_posts AS m ON
    // m.post_id = p.id where p.userid = :userid", nativeQuery = true)
    // Page<Object[]> findPostsFromLast24Hours(@Param("userid") BigInteger userid,
    // Pageable pageable);

    // @Query(value = "SELECT p.post_content, p.userid, p.likes, p.dislikes, p.id,
    // p.first_name, p.last_name, p.updated_at, p.address, m.media_content_path,
    // m.created_at "
    // + "FROM ( "
    // + " SELECT post.*, u.first_name, u.last_name, u.address "
    // + " FROM post "
    // + " LEFT JOIN users AS u ON post.userId = u.id "
    // + ") p "
    // + "LEFT JOIN media_posts AS m ON m.post_id = p.id "
    // + "WHERE p.userid = :userid", nativeQuery = true)
    // Page<Object[]> findPostsFromLast24Hours(@Param("userid") BigInteger userid,
    // Pageable pageable);

    @Query(value = "SELECT post.post_content,post.userid,post.likes,post.dislikes,post.id,post.created_at AS created_at ,u.first_name,u.last_name,post.updated_at,u.address,m.media_content_path FROM post LEFT JOIN users AS u ON post.userId = u.id LEFT JOIN media_posts AS m ON m.post_id = post.id WHERE post.userid = :userid", nativeQuery = true)
    Page<Object[]> findPostsFromLast24Hours(@Param("userid") BigInteger userid, Pageable pageable);

}
