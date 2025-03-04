package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.socialmedia.socialmediaapp.DAO.StoreMessage;

public interface MessageRepository extends JpaRepository<StoreMessage, BigInteger> {

    @Query(value = "select senderId from messages where recieverId = :userid and messages.seen=0 GROUP BY senderId", nativeQuery = true)
    public List<Object[]> findMessagesById(BigInteger userid);

    @Query(value = "select messages.messageSend,users.id,users.first_name,users.last_name from messages RIGHT JOIN users on messages.senderId=users.id and messages.recieverId=1 and messages.created_at in (SELECT MAX(messages.created_at) from messages where messages.recieverId=1 GROUP BY messages.senderId) where users.id != :userid  ORDER BY case when messages.seen = 0 THEN 0 ELSE 1 END ASC,messages.created_at DESC", nativeQuery = true)
    public List<Object[]> fetchMessagesViaLogin(BigInteger userid);
    // BigInteger userid

    @Modifying
    @Query(value = "UPDATE messages set messages.seen = 1 where messages.id = :id", nativeQuery = true)
    public void updateSeenStatus(BigInteger id);

}
