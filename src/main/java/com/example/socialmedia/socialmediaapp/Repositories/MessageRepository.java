package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.StoreMessage;

public interface MessageRepository extends JpaRepository<StoreMessage, BigInteger> {

    @Query(value = "select senderId from messages where recieverId = :userid and messages.seen=0 GROUP BY senderId", nativeQuery = true)
    public List<Object[]> findMessagesById(@Param("userid") BigInteger userid);

    @Query(value = "select messages.messageSend,users.id,users.first_name,users.last_name from messages RIGHT JOIN users on messages.senderId=users.id and messages.recieverId=1 and messages.created_at in (SELECT MAX(messages.created_at) from messages where messages.recieverId=1 GROUP BY messages.senderId) where users.id != :userid  ORDER BY case when messages.seen = 0 THEN 0 ELSE 1 END ASC,messages.created_at DESC", nativeQuery = true)
    public List<Object[]> fetchMessagesViaLogin(@Param("userid") BigInteger userid);
    // BigInteger userid

    @Modifying
    @Query(value = "UPDATE messages set messages.seen = 1 where messages.id = :id", nativeQuery = true)
    public void updateSeenStatus(@Param("id") BigInteger id);

    @Modifying
    @Query(value = "UPDATE messages set messages.seen = 1 where messages.recieverId = :id and messages.seen=0", nativeQuery = true)
    public void updateBulkSeenStatusOnRecieverId(@Param("id") BigInteger id);

    @Query(value = "SELECT COUNT(*) from messages where messages.senderId = :senderId and messages.recieverId = :receiverId and messages.seen=0", nativeQuery = true)
    public int countMessages(@Param("senderId") BigInteger senderId, @Param("receiverId") BigInteger receiverId);
}
