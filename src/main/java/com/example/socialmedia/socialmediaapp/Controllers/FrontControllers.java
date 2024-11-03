package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;

@Controller
public class FrontControllers {

    @GetMapping("/")
    public String hello() {
        return ("index");
    }

    @GetMapping("/login")
    public String createAccount() {
        return ("login");
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView("signup");

        SignUpRequest signUpRequest = new SignUpRequest();

        modelAndView.addObject("signupRequest", signUpRequest);

        return (modelAndView);
    }

}
