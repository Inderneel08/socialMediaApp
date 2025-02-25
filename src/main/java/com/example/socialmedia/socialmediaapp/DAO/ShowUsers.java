package com.example.socialmedia.socialmediaapp.DAO;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowUsers {
    private BigInteger id;

    private String first_name;

    private String last_name;

    private String email;

    private int gender;

    private String profile_photo;

    private Long approved;

    private int profile_type;
}
