package com.example.socialmedia.socialmediaapp.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/vendor/**",
                        "/fonts/**")
                .permitAll()
                .and().authorizeHttpRequests()
                .requestMatchers("/", "/login", "/signup", "/do-signup").anonymous();

        return (http.build());
    }
}
