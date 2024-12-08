package com.example.socialmedia.socialmediaapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.socialmedia.socialmediaapp.Repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfileServiceDetails {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updateProfile(String firstname, String lastname, String phone, String address, String email) {
        userRepository.updateProfile(firstname, lastname, phone, address, email);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        userDetails.setFirstName(firstname);

        userDetails.setLastName(lastname);

        userDetails.setAddress(address);

        userDetails.setPhoneNumber(phone);
    }

}
