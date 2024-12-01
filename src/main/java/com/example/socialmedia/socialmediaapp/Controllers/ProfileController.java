package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;

@Controller
public class ProfileController {

    @GetMapping("/view-profile")
    public String viewProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        model.addAttribute("email", userDetails.getUsername());

        model.addAttribute("firstname", userDetails.getFirstName());

        model.addAttribute("lastname", userDetails.getLastName());

        model.addAttribute("gender", userDetails.getGender());

        model.addAttribute("address", userDetails.getAddress());

        model.addAttribute("status", userDetails.getStatus());

        model.addAttribute("phone", userDetails.getPhoneNumber());

        model.addAttribute("profile_photo", userDetails.getProfilePhoto());

        return ("profile");
    }
}
