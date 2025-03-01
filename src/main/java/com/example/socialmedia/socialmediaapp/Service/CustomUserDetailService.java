package com.example.socialmedia.socialmediaapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserServices userServices;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // System.out.println("Loading user with email: " + email);

        Users user = userRepository.findEmail(email);

        // System.out.println(user);

        if (user == null) {
            throw new UsernameNotFoundException("Username or Password not found");
        }

        userServices.updateLastLogin(email);

        return new CustomUserDetails(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(),
                user.getPassword(),
                user.getRole(), user.getGender(), user.getAddress(), user.getStatus(), user.getPhone(),
                user.getProfile_photo(),user.getProfile_type());
    }

    // public Collection<? extends GrantedAuthority> authorities() {
    // Collection<GrantedAuthority> authorities = new ArrayList<>();

    // authorities.add(new SimpleGrantedAuthority("USER"));

    // authorities.add(new SimpleGrantedAuthority("ADMIN"));

    // return (authorities);
    // }

}
