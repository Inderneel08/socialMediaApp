package com.example.socialmedia.socialmediaapp.Service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private int role;

    public CustomUserDetails(String firstName, String lastName, String email, String password, int role) {
        this.firstName = firstName;

        this.lastName = lastName;

        this.email = email;

        this.password = password;

        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = (this.role == 1) ? "ROLE_ADMIN" : "ROLE_USER";

        return (List.of(new SimpleGrantedAuthority(roleName)));
    }

    @Override
    public String getPassword() {
        return (this.password);
    }

    @Override
    public String getUsername() {
        return (this.email);
    }

    public String getFirstName()
    {
        return(this.firstName);
    }

    public String getLastName()
    {
        return(this.lastName);
    }

    @Override
    public boolean isAccountNonExpired() {
        return (true);
    }

    @Override
    public boolean isAccountNonLocked() {
        return (true);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return (true);
    }

    @Override
    public boolean isEnabled() {
        return (true);
    }

}
