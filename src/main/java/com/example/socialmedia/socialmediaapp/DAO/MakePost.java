package com.example.socialmedia.socialmediaapp.DAO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakePost {
    private String postContent;

    private List<MultipartFile> postImages;
}
