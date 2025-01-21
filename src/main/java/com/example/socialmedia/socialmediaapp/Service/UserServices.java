package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.Aspect.SendEmail;
import com.example.socialmedia.socialmediaapp.DAO.ShowPosts;
import com.example.socialmedia.socialmediaapp.DAO.ShowUsers;
import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;
import com.example.socialmedia.socialmediaapp.DAO.Users;
import com.example.socialmedia.socialmediaapp.Repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public String doHash(String email) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(email.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing email", e);
        }
    }

    @SendEmail
    public void createUserServices(SignUpRequest signUpRequest, String ip_address, HttpServletRequest request) {

        Users users = new Users();

        users.setFirst_name(signUpRequest.getFirstName());

        users.setLast_name(signUpRequest.getLastName());

        users.setEmail(signUpRequest.getEmail());

        users.setPassword(signUpRequest.getPassword());

        users.setIp_address(ip_address);

        String emailVerificationHash = doHash(signUpRequest.getEmail());

        users.setEmail_verificationHash(emailVerificationHash);

        users.setRole(0);

        users.setDisabled(0);

        users.setBlocked(0);

        users.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        userRepository.save(users);
    }

    public boolean isEmailRegistered(String email) {

        Users users = userRepository.findEmail(email);

        if (users == null) {
            return (false);
        }

        return (true);
    }

    public String decodeEmail(String encodedEmail) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedEmail);

        String decodedEmail = new String(decodedBytes);

        return (decodedEmail);
    }

    public Users findEmailExists(String email) {
        return (userRepository.findEmail(email));
    }

    @Transactional
    public void updateVerificationStatus(String email) {
        userRepository.updateVerificationStatus(email);
    }

    @Transactional
    public void updateLastLogin(String email) {
        userRepository.updateLastLogin(email);
    }

    @Transactional
    public void updatePassword(String email, String password) {
        userRepository.updatePassword(password, email);
    }

    public Page<ShowUsers> getAllUsers(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Page<Object[]> rawresults = userRepository.explore(
                userDetails.getUserId(), pageable);

        return (rawresults.map(result -> {
            ShowUsers users = new ShowUsers();

            users.setId(BigInteger.valueOf((Long) result[0]));

            users.setFirst_name((String) result[1]);

            users.setLast_name((String) result[2]);

            users.setEmail((String) result[3]);

            users.setGender((Integer) result[13]);

            users.setProfile_photo((String) result[17]);

            users.setApproved((Integer) result[20]);

            return (users);
        }));
    }

}
