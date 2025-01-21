package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.Friends;

public interface FriendRepository extends JpaRepository<Friends, BigInteger> {

    @Query(value = "SELECT * FROM friends WHERE (friends.senderId = :userid OR friends.recieverId = :userid) AND current_status = -1", nativeQuery = true)
    List<Friends> getMyFriends(@Param("userid") BigInteger userid);

    @Query(value = "SELECT * FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId",nativeQuery = true)
    Friends getFriendsDetails(@Param("senderId") BigInteger senderId,@Param("receiverId") BigInteger receiverId);

    @Modifying
    @Query(value = "DELETE FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId",nativeQuery = true)
    void deleteAssociation(@Param("senderId") BigInteger senderId,@Param("receiverId") BigInteger receiverId);

}
