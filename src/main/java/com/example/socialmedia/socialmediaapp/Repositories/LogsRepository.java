package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.socialmedia.socialmediaapp.DAO.Logs;

public interface LogsRepository extends JpaRepository<Logs, BigInteger> {

    @Query(value = "SELECT * FROM logs where logs.userid = :id and logs.action = :action and logs.created_at >= DATE_SUB(CURRENT_TIMESTAMP,INTERVAL 30 MINUTE)", nativeQuery = true)
    Logs findPasswordChangeRequest(@Param("id") BigInteger id, @Param("action") String action);
}
