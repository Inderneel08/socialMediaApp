package com.example.socialmedia.socialmediaapp.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/vendor/**",
                        "/fonts/**")
                .permitAll()
                .and().authorizeHttpRequests()
                .requestMatchers("/", "/signup", "/do-signup", "/validate/**").anonymous()
                .and().formLogin().loginPage("/login").loginProcessingUrl("/do-login")
                .defaultSuccessUrl("/home").permitAll();

        return (http.build());
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        String secret = "";

        int saltLength = 0;

        int iterations = 0;

        SecretKeyFactoryAlgorithm algorithm = SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256;

        return new Pbkdf2PasswordEncoder(null, 0, 0, algorithm);
    }

}
