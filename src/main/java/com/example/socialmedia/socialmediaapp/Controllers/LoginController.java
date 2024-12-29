package com.example.socialmedia.socialmediaapp.Controllers;

import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.socialmedia.socialmediaapp.Aspect.ExtractEmail;
import com.example.socialmedia.socialmediaapp.Aspect.SendPasswordResetEmail;
import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;
import com.example.socialmedia.socialmediaapp.DAO.Users;
import com.example.socialmedia.socialmediaapp.Service.GetClientIP;
import com.example.socialmedia.socialmediaapp.Service.LogsServiceDetails;
import com.example.socialmedia.socialmediaapp.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private LogsServiceDetails logsServiceDetails;

    @Autowired
    private GetClientIP getClientIP;

    // @PostMapping("/do-login")
    // public ModelAndView doLogin(@ModelAttribute LoginRequest loginRequest,
    // HttpServletRequest request) {

    // }

    @PostMapping("/change-password")
    @SendPasswordResetEmail
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        String email = payload.get("email");

        if (!userServices.isEmailRegistered(email)) {
            return ResponseEntity.badRequest()
                    .body("You will get an email for changing the password if it exists in our records.");
        }

        Users users = userServices.findEmailExists(email);

        if (logsServiceDetails.findPasswordChangeRequest(users.getId(), "Password_Change_Request") != null) {
            return ResponseEntity.badRequest()
                    .body("Password change has been requested prior.");
        }

        if (logsServiceDetails.findPasswordChangeRequest(users.getId(),
                "Password_Changed") != null) {
            return ResponseEntity.badRequest()
                    .body("Password change has been requested prior.");
        }

        logsServiceDetails.createLogs(users.getId(), "Password_Change_Request");

        return ResponseEntity.ok("You will get an email for changing the password if it exists in our records.");
    }

    @PostMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestBody Map<String, String> payload) {
        String password = payload.get("password");

        String encodedEmail = payload.get("email");

        byte[] decodedBytes = Base64.getDecoder().decode(encodedEmail);

        String decodedEmail = new String(decodedBytes);

        if (userServices.findEmailExists(decodedEmail) == null) {
            return (ResponseEntity.badRequest().body("Some unexpected error happened!"));
        }

        userServices.updatePassword(decodedEmail, password);

        Users users = userServices.findEmailExists(decodedEmail);

        logsServiceDetails.createLogs(users.getId(), "Password_Changed");

        return (ResponseEntity.ok("Password updated successfully!"));
    }

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
