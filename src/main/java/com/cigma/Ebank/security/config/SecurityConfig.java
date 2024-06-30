package com.cigma.Ebank.security.config;

import com.cigma.Ebank.security.filters.JwtAuthenticationFilter;
import com.cigma.Ebank.security.filters.JwtAuthorizationFilter;
import com.cigma.Ebank.security.repositories.AppUserRepository;
import com.cigma.Ebank.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin("*")
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AppUserRepository appUserRepository;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AppUserRepository appUserRepository) {
        this.userDetailsService = userDetailsService;
        this.appUserRepository = appUserRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()); // Disable CSRF

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Use stateless sessions

        //http.cors(); // Enable CORS

        http.headers(headers -> {
            headers.frameOptions(frameOptions -> frameOptions.disable()); // Disable frame options
            headers.referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)); // Set referrer policy
        });

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**", "/refreshToken/**", "/login/**").permitAll() // Allow specific requests
                .anyRequest().authenticated() // Authenticate all other requests
        );

        http.addFilter(new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), appUserRepository));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
