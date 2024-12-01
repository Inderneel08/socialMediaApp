package com.example.socialmedia.socialmediaapp.Security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class EmailCaptureFilter extends OncePerRequestFilter {

    private static final ThreadLocal<String> currentEmail = new ThreadLocal<>();

    private static final ThreadLocal<String> currentPassword = new ThreadLocal<>();

    private static final ThreadLocal<String> currentHashString = new ThreadLocal<>();

    public static String getEmail() {
        return currentEmail.get();
    }

    public static String getPassword() {
        return currentPassword.get();
    }

    public static String getHashString() {
        return currentHashString.get();
    }

    public static void clear() {
        currentEmail.remove();

        currentPassword.remove();

        currentHashString.remove();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (("/do-login").equals(request.getRequestURI())) {
            String email = request.getParameter("email");

            String password = request.getParameter("password");

            // System.out.println(email);

            // System.out.println(password);

            String hashString = request.getParameter("hashString");

            if (email != null && password != null) {
                currentEmail.set(email);

                currentPassword.set(password);

                currentHashString.set(hashString);

                try {
                    filterChain.doFilter(request, response);
                } finally {
                    clear();
                }

            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
