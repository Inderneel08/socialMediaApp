package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.socialmedia.socialmediaapp.Aspect.ExtractEmail;
import com.example.socialmedia.socialmediaapp.DAO.LoginRequest;
import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;
import com.example.socialmedia.socialmediaapp.DAO.Users;
import com.example.socialmedia.socialmediaapp.Service.GetClientIP;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private GetClientIP getClientIP;

    // @PostMapping("/do-login")
    // public ModelAndView doLogin(@ModelAttribute LoginRequest loginRequest,
    // HttpServletRequest request) {

    // }

    @PostMapping("/do-signup")
    @ExtractEmail
    public ModelAndView doSignUp(@ModelAttribute SignUpRequest signUpRequest, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("redirect:/signup?redirected=true");

        if (userServices.isEmailRegistered(signUpRequest.getEmail())) {
            // modelAndView.addObject("errorMessage", "Email already registered!");

            request.getSession().setAttribute("errorMessage", "Email already registered!");

            return (modelAndView);
        }

        String ip_address = getClientIP.getClientIp(request);

        userServices.createUserServices(signUpRequest, ip_address, request);

        // modelAndView.addObject("successMessage", "Email registered successfully!");

        request.getSession().setAttribute("successMessage", "Email registered successfully!");

        return (modelAndView);
    }

    @GetMapping("/validate/{encodedEmail}/{hashGenerated}")
    public ModelAndView validateEmail(@PathVariable String encodedEmail, @PathVariable String hashGenerated) {
        ModelAndView modelAndView = new ModelAndView("confirmation");

        String decodedEmail = userServices.decodeEmail(encodedEmail);

        // System.out.println(decodedEmail);

        Users users = userServices.findEmailExists(decodedEmail);

        String hashString = "A#197012";

        // System.out.println(userServices.doHash(users.getEmail_verificationHash() +
        // hashString));

        // System.out.println(actualHash);

        // System.out.println();

        // System.out.println(userServices.doHash(users.getEmail()));

        if (users == null) {
            modelAndView.addObject("value", "Email confirmation failed!");
        } else {
            String actualHash = userServices.doHash(users.getEmail_verificationHash() + hashString);
            if (actualHash.equals(hashGenerated)) {
                if (users.getIs_verified() == 1) {
                    modelAndView.addObject("value", "Email already verified!");
                } else {
                    userServices.updateVerificationStatus(decodedEmail);
                    modelAndView.addObject("value", "Email confirmation successful!");
                }
            } else {
                modelAndView.addObject("value", "Email confirmation failed!");
            }
        }

        return (modelAndView);
    }

}
