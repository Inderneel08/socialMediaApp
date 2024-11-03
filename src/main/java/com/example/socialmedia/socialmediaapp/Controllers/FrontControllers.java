package com.example.socialmedia.socialmediaapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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

        // Users users = new Users();

        // modelAndView.addObject("users", users);

        return (modelAndView);
    }

}
