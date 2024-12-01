package com.example.socialmedia.socialmediaapp.Controllers;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // FOR 404 ERROR
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoResourceFoundException ex, Model model) {
        model.addAttribute("error", "404");
        model.addAttribute("message", "The page you are looking for does not exist.");

        return ("error");
    }

    // FOR 403 ERROR
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("error", "403");

        model.addAttribute("message", "An unexpected error occurred. Please try again later.");

        return ("error");
    }

    // FOR 500 ERROR
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralError(Exception ex, Model model) {
        model.addAttribute("error", "500");

        model.addAttribute("message", "An unexpected error occurred. Please try again later.");

        return ("error");
    }

}
