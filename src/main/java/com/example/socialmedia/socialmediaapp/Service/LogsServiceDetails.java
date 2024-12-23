package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.DAO.Logs;
import com.example.socialmedia.socialmediaapp.Repositories.LogsRepository;

@Service
public class LogsServiceDetails {

    @Autowired
    private LogsRepository logsRepository;

    public void createLogs(BigInteger userid, String action) {
        Logs logs = new Logs();

        logs.setUserid(userid);

        logs.setAction(action);

        logsRepository.save(logs);
    }

    public Logs findPasswordChangeRequest(BigInteger userid,String action) {
        return (logsRepository.findPasswordChangeRequest(userid, action));
    }

}
