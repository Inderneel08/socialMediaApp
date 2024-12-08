package com.example.socialmedia.socialmediaapp.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.ProfileServiceDetails;

@Controller
public class ProfileController {

    @Autowired
    private ProfileServiceDetails profileServiceDetails;

    @GetMapping("/view-profile")
    public String viewProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        model.addAttribute("email", userDetails.getUsername());

        model.addAttribute("firstname", userDetails.getFirstName());

        model.addAttribute("lastname", userDetails.getLastName());

        model.addAttribute("gender", userDetails.getGender());

        model.addAttribute("address", userDetails.getAddress());

        model.addAttribute("phone", userDetails.getPhoneNumber());

        model.addAttribute("profile_photo", userDetails.getProfilePhoto());

        return ("profile");
    }

    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> profileData) {

        String firstname = profileData.get("firstname");

        String lastname = profileData.get("lastname");

        String phone = profileData.get("phone");

        String address = profileData.get("address");

        String email = profileData.get("email");

        profileServiceDetails.updateProfile(firstname, lastname, phone, address, email);

        return (ResponseEntity.ok().build());
    }

}
