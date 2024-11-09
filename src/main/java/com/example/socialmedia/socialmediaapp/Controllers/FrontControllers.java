package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.socialmedia.socialmediaapp.DAO.LoginRequest;
import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FrontControllers {

    @GetMapping("/")
    public String hello() {
        return ("index");
    }

    @GetMapping("/login")
    public ModelAndView createAccount() {
        ModelAndView modelAndView = new ModelAndView("login");

        LoginRequest loginRequest = new LoginRequest();

        modelAndView.addObject("loginRequest", loginRequest);

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
