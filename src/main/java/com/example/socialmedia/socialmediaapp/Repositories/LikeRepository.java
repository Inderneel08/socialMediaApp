package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.Likes;

public interface LikeRepository extends JpaRepository<Likes, BigInteger> {

    @Query(value = "SELECT * FROM likes where likes.post_id = :post_id and likes.userid = :userid", nativeQuery = true)
    Likes findLikes(@Param("post_id") BigInteger post_id, @Param("userid") BigInteger userid);

    @Modifying
    @Query(value = "UPDATE likes set likes.action=1 where likes.post_id = :post_id and likes.userid = :userid", nativeQuery = true)
    void likePost(@Param("post_id") BigInteger post_id, @Param("userid") BigInteger userid);

    @Modifying
    @Query(value = "UPDATE likes set likes.action=0 where likes.post_id = :post_id and likes.userid = :userid", nativeQuery = true)
    void unlikePost(@Param("post_id") BigInteger post_id, @Param("userid") BigInteger userid);
}
