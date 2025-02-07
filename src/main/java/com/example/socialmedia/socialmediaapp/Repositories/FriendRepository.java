package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.socialmedia.socialmediaapp.DAO.Friends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendRepository extends JpaRepository<Friends, BigInteger> {

    @Query(value = "SELECT u.first_name,u.last_name,u.profile_photo FROM friends LEFT JOIN users as u on u.id=friends.senderId where friends.recieverId = :userid and friends.current_status=1", nativeQuery = true)
    Page<Object[]> getMyFriends(@Param("userid") BigInteger userid, Pageable pageable);

    @Query(value = "SELECT * FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
    Friends getFriendsDetails(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

    @Modifying
    @Query(value = "DELETE FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
    void deleteAssociation(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

    @Modifying
    @Query(value = "UPDATE friends set friends.current_status=1 where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
    void addFriendship(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);
}
