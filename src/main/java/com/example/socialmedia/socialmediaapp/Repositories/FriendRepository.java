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

        @Query(value = "SELECT u.id,u.first_name,u.last_name,u.profile_photo FROM users as u where (u.first_name LIKE CONCAT('%', :message, '%') or u.last_name LIKE CONCAT('%', :message, '%') or CONCAT(u.first_name,' ',u.last_name) LIKE CONCAT('%', :message, '%') ) and u.id!=:userid ORDER BY u.created_at DESC", nativeQuery = true)
        Page<Object[]> getUsersList(@Param("userid") BigInteger userid, Pageable pageable,
                        @Param("message") String message);

        @Query(value = "select allRecords.id,allRecords.first_name,allRecords.last_name,allRecords.profile_photo,allRecords.messageSend,allRecords.typeofMessage,allRecords.seen from ( select finalRecords.*,messages.messageSend,messages.seen,1 as typeofMessage from( select records.*,max(messages.id) as messageId from ( select users.* from messages inner join users on users.id=messages.senderId where messages.recieverId = :userid  group by users.id ) as records inner join messages on messages.senderId=records.id where messages.recieverId = :userid group by records.id ) as finalRecords inner join messages on messages.id=finalRecords.messageId union all select finalRecords.*,messages.messageSend,messages.seen,0 as typeofMessage from ( select records.*,max(messages.id) as messageId from ( select users.* from messages inner join users on users.id=messages.recieverId where messages.senderId = :userid group by users.id ) as records inner join messages on messages.recieverId=records.id where messages.senderId = :userid group by records.id ) as finalRecords inner join messages on finalRecords.messageId=messages.id ) as allRecords", nativeQuery = true)
        Page<Object[]> getMyMessages(@Param("userid") BigInteger userid, Pageable pageable);

        @Query(value = "SELECT * FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
        Friends getFriendsDetails(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

        @Modifying
        @Query(value = "DELETE FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
        void deleteAssociation(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

        @Modifying
        @Query(value = "UPDATE friends set friends.current_status=1 where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
        void addFriendship(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);

        @Query(value = "SELECT * FROM friends where friends.senderId = :senderId and friends.recieverId = :receiverId", nativeQuery = true)
        Friends getFriendsRequestStatus(@Param("senderId") BigInteger senderId,
                        @Param("receiverId") BigInteger receiverId);
}
