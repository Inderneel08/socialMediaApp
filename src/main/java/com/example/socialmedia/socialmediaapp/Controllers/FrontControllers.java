package com.example.socialmedia.socialmediaapp.Controllers;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.socialmedia.socialmediaapp.DAO.LoginRequest;
import com.example.socialmedia.socialmediaapp.DAO.MakePost;
import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;
import com.example.socialmedia.socialmediaapp.DAO.Users;
import com.example.socialmedia.socialmediaapp.Security.EmailCaptureFilter;
import com.example.socialmedia.socialmediaapp.Service.ChatServiceLayer;
import com.example.socialmedia.socialmediaapp.Service.CustomUserDetails;
import com.example.socialmedia.socialmediaapp.Service.FriendRequestServiceLayer;
import com.example.socialmedia.socialmediaapp.Service.LogsServiceDetails;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FrontControllers {

    @Autowired
    private UserServices userServices;

    @Autowired
    private LogsServiceDetails logsServiceDetails;

    @Autowired
    private FriendRequestServiceLayer friendRequestServiceLayer;

    @Autowired
    private ChatServiceLayer chatServiceLayer;

    @GetMapping("/")
    public String hello() {
        return ("index");
    }

    @GetMapping("/forgot-password")
    public ModelAndView forgotPassword() {
        ModelAndView modelAndView = new ModelAndView("forgot");

        return (modelAndView);
    }

    @GetMapping("/showPassword/{encodedEmail}")
    public String showPasswordResetPage(Model model, @PathVariable(value = "encodedEmail") String encodedEmail) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedEmail);

        String decodedEmail = new String(decodedBytes);

        if (userServices.findEmailExists(decodedEmail) == null) {
            model.addAttribute("iwalp", "Some error happened!");

            return ("commonError");
        }

        Users users = userServices.findEmailExists(decodedEmail);

        if (logsServiceDetails.findPasswordChangeRequest(users.getId(), "Password_Change_Request") == null) {
            model.addAttribute("iwalp", "Page has expired since link opened after 30 minutes!");

            return ("commonError");
        }

        if (logsServiceDetails.findPasswordChangeRequest(users.getId(), "Password_Changed") != null) {
            model.addAttribute("iwalp", "Password cannot be changed after 30 minutes of setting it.");

            return ("commonError");
        }

        model.addAttribute("encodedEmail", encodedEmail);

        return ("changePassword");
    }

    @GetMapping("/home")
    public String home(Model model, @RequestParam(value = "redirected", required = false) boolean redirected,
            HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {

            CustomUserDetails userDetails = (CustomUserDetails) principal;

            MakePost postContent = new MakePost();

            model.addAttribute("firstname", userDetails.getFirstName());

            model.addAttribute("lastname", userDetails.getLastName());

            model.addAttribute("gender", userDetails.getGender());

            model.addAttribute("profile_image_path", userDetails.getProfilePhoto());

            model.addAttribute("postContent", postContent);

            model.addAttribute("notificationCount", userServices.getNotificationsCount(userDetails.getUserId()));

            model.addAttribute("userId", userDetails.getUserId());

            model.addAttribute("countMessages", chatServiceLayer.computeMessageSize(userDetails.getUserId()));

            // System.out.println("Count-> " + chatServiceLayer.computeMessageSize(userDetails.getUserId()));

            // model.addAttribute("friendRequests",
            // friendRequestServiceLayer.getAllFriends(userDetails.getUserId(), 0));

            if (!redirected) {
                request.getSession().removeAttribute("successMessage");

                request.getSession().removeAttribute("errorMessage");
            }

        } else {
            return ("login");
        }

        return ("index");
    }

    @GetMapping("/login")
    public ModelAndView createAccount(@RequestParam(value = "redirected", required = false) boolean redirected,
            HttpServletRequest request) {

        if (redirected) {

            if (request.getSession().getAttribute("errorCode").equals(0)) {
                String email = (String) request.getSession().getAttribute("email");

                userServices.resendEmail(email, request);

                request.removeAttribute("email");

                request.removeAttribute("errorCode");
            }
        }

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
