package com.example.socialmedia.socialmediaapp.Aspect;

import java.util.Base64;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.socialmedia.socialmediaapp.DAO.SignUpRequest;
import com.example.socialmedia.socialmediaapp.Service.EmailService;
import com.example.socialmedia.socialmediaapp.Service.UserServices;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class MainAspect {

    private String extractedEmail;

    @Autowired
    private UserServices userServices;

    @Autowired
    private EmailService emailService;

    @Before("@annotation(com.example.socialmedia.socialmediaapp.Aspect.ExtractEmail) && args(signUpRequest,..)")
    public void extractedEmail(JoinPoint joinPoint, SignUpRequest signUpRequest) {
        extractedEmail = signUpRequest.getEmail();
    }

    @After("@annotation(com.example.socialmedia.socialmediaapp.Aspect.SendEmail) && args(signUpRequest,ip_address,request,..)")
    public void sendConfirmationEmail(JoinPoint joinPoint, SignUpRequest signUpRequest, String ip_address,
            HttpServletRequest request) {

        String hashString = "A#197012";

        String appBaseurl = request.getScheme() + "://" + request.getServerName() +
                ":" + request.getServerPort() + "/";

        String encodedEmail = Base64.getEncoder().encodeToString(signUpRequest.getEmail().getBytes());

        String emailVerificationHash = userServices.doHash(signUpRequest.getEmail());

        String urlHash = userServices.doHash(emailVerificationHash + hashString);

        String appUrl = appBaseurl + "validate/" + encodedEmail + "/" + urlHash;

        String subject = "Confirm your email registered to mySocial";

        String emailContent = "<p>Thank you for registering! Please confirm your email by clicking the link below:</p>";
        emailContent += "<p><a href=\"" + appUrl + "\">Confirm Email</a></p>";
        emailContent += "<p>If the link doesn't work, copy and paste the following URL into your browser:</p>";
        emailContent += "<p>" + appUrl + "</p>"; // You can remove this line if you don't want to show the raw
                                                 // URL

        emailService.sendConfirmationEmail(signUpRequest.getEmail(), subject, emailContent);
    }

}
