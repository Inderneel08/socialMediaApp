package com.example.socialmedia.socialmediaapp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.socialmedia.socialmediaapp.DAO.Users;
import com.example.socialmedia.socialmediaapp.Repositories.UserRepository;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

public class CustomPasswordEncoder implements PasswordEncoder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Override
    public String encode(CharSequence rawPassword) {
        // System.out.println("Inside encode...");

        String email = EmailCaptureFilter.getEmail();

        Users user = userRepository.findEmail(email);

        if (user != null) {
            return (user.getPassword());
        }

        return (null);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // System.out.println("Enter matches");

        String password = EmailCaptureFilter.getPassword();

        String email = EmailCaptureFilter.getEmail();

        String hashString = EmailCaptureFilter.getHashString();

        Users user = userRepository.findEmail(email);

        if (user == null) {
            return (false);
        }

        if (userServices.doHash(user.getPassword() + hashString).equals(password)) {
            return (true);
        }

        return (false);
    }

}
