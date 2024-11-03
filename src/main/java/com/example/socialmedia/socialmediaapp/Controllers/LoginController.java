package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.socialmedia.socialmediaapp.Aspect.ExtractEmail;
import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;
import com.example.socialmedia.socialmediaapp.Service.GetClientIP;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private GetClientIP getClientIP;

    @PostMapping("/do-login")
    public void doLogin() {

    }

    @PostMapping("/do-signup")
    @ExtractEmail
    public ModelAndView doSignUp(@ModelAttribute SignUpRequest signUpRequest, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("redirect:/signup");

        if (userServices.isEmailRegistered(signUpRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already registered!");

            return (modelAndView);
        }

        String ip_address = getClientIP.getClientIp(request);

        userServices.createUserServices(signUpRequest, ip_address, request);

        redirectAttributes.addFlashAttribute("successMessage", "Email registered successfully!");

        return (modelAndView);
    }

    @PostMapping("/validate/{encodedEmail}/{hashGenerated}")
    public void validateEmail(@PathVariable String encodedEmail, @PathVariable String hashGenerated) {

    }

}
