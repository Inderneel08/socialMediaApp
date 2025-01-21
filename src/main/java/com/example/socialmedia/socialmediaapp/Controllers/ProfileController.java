package com.example.socialmedia.socialmediaapp.Controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.ProfileServiceDetails;
import com.example.socialmedia.socialmediaapp.Service.UploadServiceDetails;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private ProfileServiceDetails profileServiceDetails;

    @Autowired
    private UploadServiceDetails uploadServiceDetails;

    @Autowired
    private UserServices userServices;

    @GetMapping("/view-profile")
    public String viewProfile(
            @RequestParam(value = "redirected", required = false) boolean redirected, HttpServletRequest request,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        model.addAttribute("email", userDetails.getUsername());

        model.addAttribute("firstname", userDetails.getFirstName());

        model.addAttribute("lastname", userDetails.getLastName());

        model.addAttribute("gender", userDetails.getGender());

        model.addAttribute("address", userDetails.getAddress());

        model.addAttribute("phone", userDetails.getPhoneNumber());

        model.addAttribute("profile_photo", userDetails.getProfilePhoto());

        model.addAttribute("profileType", userDetails.getProfileType());

        if (!redirected) {
            request.getSession().removeAttribute("successMessage");

            request.getSession().removeAttribute("errorMessage");
        }

        return ("profile");
    }

    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> profileData) {

        String firstname = profileData.get("firstname");

        String lastname = profileData.get("lastname");

        String phone = profileData.get("phone");

        String address = profileData.get("address");

        String email = profileData.get("email");

        String profileType = profileData.get("profile_type");

        int profile_type;

        if (profileType.equals("public")) {
            profile_type = 0;
        } else {
            profile_type = 1;
        }

        profileServiceDetails.updateProfile(firstname, lastname, phone, address,
                email, profile_type);

        return (ResponseEntity.ok().build());
    }

    @PostMapping("/upload-profile-photo")
    public ModelAndView uploadProfilePhoto(@RequestParam("profile-photo") MultipartFile file,
            HttpServletRequest request) {
        try {
            if (file.isEmpty()) {
                // Please upload a file.

                request.getSession().setAttribute("errorMessage", "Please upload a file!");

                ModelAndView modelAndView = new ModelAndView(
                        "redirect:/view-profile?redirected=false");

                return (modelAndView);
            }

            String contentType = file.getContentType();

            // Please upload a file with type png,jpeg and jpg.

            if (!(("image/png").equals(contentType) || ("image/jpeg").equals(contentType)
                    || ("image/jpg").equals(contentType))) {

                request.getSession().setAttribute("errorMessage", "Please upload an image of type .png, .jpeg or .jpg");

                ModelAndView modelAndView = new ModelAndView(
                        "redirect:/view-profile?redirected=false");

                return (modelAndView);
            }
            String uploadDirectory = "src/main/resources/static/images/profilePhotos";

            String filename = UUID.randomUUID().toString() + "_" + LocalDate.now().toString() + "_"
                    + file.getOriginalFilename();

            uploadServiceDetails.upload(file, filename, uploadDirectory, "profile-photo");

            request.getSession().setAttribute("successMessage",
                    "Image was uploaded was successfully");

            // File upload successfull.
            ModelAndView modelAndView = new ModelAndView(
                    "redirect:/view-profile?redirected=true");

            return (modelAndView);
        } catch (Exception e) {
            e.printStackTrace();

            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            // .body("An error occurred while uploading the file.");

            // Error occured 500 error.
            request.getSession().setAttribute("errorMessage", "An unexpected error happened while uploading the file.");

            ModelAndView modelAndView = new ModelAndView(
                    "redirect:/view-profile?redirected=false");

            return (modelAndView);
        }
    }

    @PostMapping("/changeMyPassword")
    public ResponseEntity<?> changeMyPassword(@RequestBody Map<String, String> passwords) {

        String password = passwords.get("password");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        userServices.updatePassword(userDetails.getUsername(), password);

        return (ResponseEntity.ok().body("Password changed successfully"));
    }

}
