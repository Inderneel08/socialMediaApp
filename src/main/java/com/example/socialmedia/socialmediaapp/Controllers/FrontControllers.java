package com.example.socialmedia.socialmediaapp.Controllers;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.socialmedia.socialmediaapp.DAO.LoginRequest;
import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;
import com.example.socialmedia.socialmediaapp.Security.EmailCaptureFilter;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetailService;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FrontControllers {

    @Autowired
    private UserServices userServices;

    @Autowired
    private EmailCaptureFilter emailCaptureFilter;

    @GetMapping("/")
    public String hello() {
        return ("index");
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {

            CustomUserDetails userDetails = (CustomUserDetails) principal;

            model.addAttribute("firstname", userDetails.getFirstName());

            model.addAttribute("lastname", userDetails.getLastName());

            model.addAttribute("gender", userDetails.getGender());

            model.addAttribute("profile_image_path", userDetails.getProfilePhoto());
        } else {
            return ("login");
        }

        return ("index");
    }

    @GetMapping("/login")
    public ModelAndView createAccount(@RequestParam(value = "redirected", required = false) boolean redirected,
            HttpServletRequest request) {

        if (!redirected) {
            request.getSession().removeAttribute("errorMessage");
        }

        ModelAndView modelAndView = new ModelAndView("login");

        LoginRequest loginRequest = new LoginRequest();

        modelAndView.addObject("loginRequest", loginRequest);

        SecureRandom random = new SecureRandom();

        byte[] randomBytes = new byte[32];

        random.nextBytes(randomBytes);

        String hashString = Base64.getEncoder().withoutPadding().encodeToString(randomBytes);

        // 5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975

        // System.out.println(userServices.doHash(
        // "5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975" +
        // hashString));

        // emailCaptureFilter

        request.getSession().setAttribute("hashString", hashString);

        return (modelAndView);
    }

    @GetMapping("/signup")
    public ModelAndView signup(@RequestParam(value = "redirected", required = false) boolean redirected,
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("signup");

        SignUpRequest signUpRequest = new SignUpRequest();

        modelAndView.addObject("signupRequest", signUpRequest);

        if (!redirected) {
            request.getSession().removeAttribute("successMessage");

            request.getSession().removeAttribute("errorMessage");
        }

        return (modelAndView);
    }

}
