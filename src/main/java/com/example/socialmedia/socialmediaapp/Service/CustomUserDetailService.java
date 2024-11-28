package com.example.socialmedia.socialmediaapp.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.socialmedia.socialmediaapp.DAO.Users;
import com.example.socialmedia.socialmediaapp.Repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // System.out.println("Loading user with email: " + email);

        Users user = userRepository.findEmail(email);

        // System.out.println(user);

        if (user == null) {
            throw new UsernameNotFoundException("Username or Password not found");
        }

        return new CustomUserDetails(user.getEmail(), user.getPassword(), user.getRole());
    }

    // public Collection<? extends GrantedAuthority> authorities() {
    //     Collection<GrantedAuthority> authorities = new ArrayList<>();

    //     authorities.add(new SimpleGrantedAuthority("USER"));

    //     authorities.add(new SimpleGrantedAuthority("ADMIN"));

    //     return (authorities);
    // }

}
