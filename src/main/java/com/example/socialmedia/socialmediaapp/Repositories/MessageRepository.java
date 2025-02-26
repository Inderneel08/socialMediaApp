package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.socialmedia.socialmediaapp.DAO.StoreMessage;

public interface MessageRepository extends JpaRepository<StoreMessage, BigInteger> {

    @Query(value = "select senderId from messages where recieverId = :userid and messages.seen=0 GROUP BY senderId", nativeQuery = true)
    public List<Object[]> findMessagesById(BigInteger userid);
}
