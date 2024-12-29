package com.example.socialmedia.socialmediaapp.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.socialmedia.socialmediaapp.DAO.MediaPosts;
import com.example.socialmedia.socialmediaapp.Repositories.MediaRepository;
import com.example.socialmedia.socialmediaapp.Repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UploadServiceDetails {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Transactional
    public void upload(MultipartFile file, String filename, String uploadDirectory, String related,BigInteger post_id)
    {
        Path uploadPath = Paths.get(uploadDirectory);

        Path filePath = uploadPath.resolve(filename);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            if (related.equals("post-photo")) {
                MediaPosts mediaPosts = new MediaPosts();

                mediaPosts.setMedia_content_path("images/postPictures/"+filename);

                mediaPosts.setPost_id(post_id);

                mediaRepository.save(mediaPosts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void upload(MultipartFile file, String filename, String uploadDirectory, String related) {
        Path uploadPath = Paths.get(uploadDirectory);

        Path filePath = uploadPath.resolve(filename);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            if (related.equals("profile-photo")) {
                userRepository.updateProfilePhoto("images/profilePhotos/" + filename, userDetails.getUsername());

                userDetails.setProfilePhoto("images/profilePhotos/" + filename);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
