package com.example.socialmedia.socialmediaapp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.socialmedia.socialmediaapp.Service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class Security {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private EmailCaptureFilter emailCaptureFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/vendor/**",
                        "/fonts/**")
                .permitAll()
                .and().authorizeHttpRequests()
                .requestMatchers("/", "/signup", "/do-signup", "/validate/**").anonymous()
                .requestMatchers("/home").hasRole("USER")
                .and().formLogin().loginPage("/login").loginProcessingUrl("/do-login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home").permitAll()
                .and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").permitAll();

        http.addFilterBefore(emailCaptureFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());

        return (http.build());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // System.out.println("Using CustomPasswordEncoder");
        return new CustomPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        AuthenticationManager manager = config.getAuthenticationManager();

        return manager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
            throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        // System.out.println("DaoAuthenticationProvider configured");
        return provider;
    }
}
