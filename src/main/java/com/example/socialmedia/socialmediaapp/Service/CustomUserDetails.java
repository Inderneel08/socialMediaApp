package com.example.socialmedia.socialmediaapp.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private BigInteger userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private int role;

    private int gender;

    private String address;

    private String status;

    private String phone;

    private String profile_photo;

    private int profile_type;

    public CustomUserDetails(BigInteger userId, String firstName, String lastName, String email, String password,
            int role, int gender,
            String address, String status, String phone, String profile_photo,int profile_type) {

        this.userId = userId;

        this.firstName = firstName;

        this.lastName = lastName;

        this.email = email;

        this.password = password;

        this.role = role;

        this.gender = gender;

        this.address = address;

        this.status = status;

        this.phone = phone;

        this.profile_photo = profile_photo;

        this.profile_type = profile_type;
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

    public String getFirstName() {
        return (this.firstName);
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return (this.lastName);
    }

    public void setProfileType(int profile_type)
    {
        this.profile_type=profile_type;
    }

    public int getProfileType()
    {
        return(this.profile_type);
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getGender() {
        if (this.gender == -1) {
            return ("LGBTQ");
        } else if (this.gender == 0) {
            return ("MALE");
        }

        return ("FEMALE");
    }

    public String getAddress() {
        return (this.address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return (this.status);
    }

    public String getPhoneNumber() {
        return (this.phone);
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    public String getProfilePhoto() {
        return (this.profile_photo);
    }

    public void setProfilePhoto(String profile_photo)
    {
        this.profile_photo = profile_photo;
    }

    public BigInteger getUserId() {
        return (this.userId);
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
