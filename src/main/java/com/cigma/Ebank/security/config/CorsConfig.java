package com.cigma.Ebank.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply these rules to all routes
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH") // Allowed HTTP methods
                        .allowedOrigins("*") // Allowed domains
                        .allowedHeaders("*"); // Allowed HTTP headers
            }
        };
    }
}
