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

        @Query(value = "SELECT u.id,u.first_name,u.last_name,u.profile_photo FROM friends LEFT JOIN users as u on u.id=friends.senderId where friends.recieverId = :userid and friends.current_status=1 and (u.first_name LIKE CONCAT('%', :message, '%') or u.last_name LIKE CONCAT('%', :message, '%') or CONCAT(u.first_name,' ',u.last_name) LIKE CONCAT('%', :message, '%') )", nativeQuery = true)
        Page<Object[]> getWhoIFollow(@Param("userid") BigInteger userid, Pageable pageable,
                        @Param("message") String message);

        @Query(value = "SELECT u.id,u.first_name,u.last_name,u.profile_photo FROM friends LEFT JOIN users as u on u.id=friends.recieverId where friends.senderId = :userid and friends.current_status=1 and (u.first_name LIKE CONCAT('%', :message, '%') or u.last_name LIKE CONCAT('%', :message, '%') or CONCAT(u.first_name,' ',u.last_name) LIKE CONCAT('%', :message, '%') )", nativeQuery = true)
        Page<Object[]> getWhoFollowMe(@Param("userid") BigInteger userid, Pageable pageable,
                        @Param("message") String message);

        @Query(value = "SELECT * FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
        Friends getFriendsDetails(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

        @Modifying
        @Query(value = "DELETE FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
        void deleteAssociation(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

        @Modifying
        @Query(value = "UPDATE friends set friends.current_status=1 where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
        void addFriendship(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);
}
