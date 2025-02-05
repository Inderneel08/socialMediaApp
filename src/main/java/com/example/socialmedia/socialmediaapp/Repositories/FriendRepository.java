package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.Friends;
import com.example.socialmedia.socialmediaapp.DAO.MyFriends;

public interface FriendRepository extends JpaRepository<Friends, BigInteger> {

    @Query(value = "SELECT u.first_name,u.last_name,u.profile_photo FROM friends LEFT JOIN users as u on u.id=friends.senderId where recieverId = :userid and current_status=1", nativeQuery = true)
    List<MyFriends> getMyFriends(@Param("userid") BigInteger userid);

    @Query(value = "SELECT * FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
    Friends getFriendsDetails(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

    @Modifying
    @Query(value = "DELETE FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
    void deleteAssociation(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

    @Modifying
    @Query(value = "UPDATE friends set friends.current_status=1 where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
    void addFriendship(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);
}
