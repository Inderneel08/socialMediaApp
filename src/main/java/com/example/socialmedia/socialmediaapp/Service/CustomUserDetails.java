package com.example.socialmedia.socialmediaapp.Service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.email=email;

        this.password=password;

        this.authorities=authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return(this.authorities);
    }

    @Override
    public String getPassword() {
        return(this.password);
    }

    @Override
    public String getUsername() {
        return(this.email);
    }

    @Override
    public boolean isAccountNonExpired() {
        return(false);
    }

    @Override
    public boolean isAccountNonLocked() {
        return(false);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return(false);
    }

    @Override
    public boolean isEnabled() {
        return(true);
    }

}
