package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "SELECT post.post_content,post.userid,post.likes,post.dislikes,post.id,CASE WHEN l.userid = :userid AND l.action != 0 THEN TRUE ELSE FALSE END AS liked,post.created_at,u.first_name,u.last_name,post.updated_at,u.address,m.media_content_path FROM post LEFT JOIN users AS u ON post.userId = u.id LEFT JOIN media_posts AS m ON m.post_id = post.id LEFT JOIN likes AS l ON l.post_id = post.id AND ( (l.userid = :userid) OR (l.userid != :userid AND NOT EXISTS ( SELECT * FROM likes l2 WHERE l2.post_id = post.id AND l2.userid = :userid ) ) ) ORDER BY post.created_at DESC", nativeQuery = true)
    Page<Object[]> findPostsFromLast24Hours(@Param("userid") BigInteger userid, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE post set post.likes = post.likes+1 where post.id = :postId", nativeQuery = true)
    void increaseLikeCount(@Param("postId") BigInteger postId);

    @Modifying
    @Query(value = "UPDATE post set post.likes = post.likes-1 where post.id = :postId", nativeQuery = true)
    void decreaseLikeCount(@Param("postId") BigInteger postId);

}
