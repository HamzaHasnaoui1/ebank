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
                registry.addMapping("/**") // Appliquer ces règles à toutes les routes
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH") // Méthodes HTTP autorisées
                        .allowedOrigins("*") // Domaines autorisés à faire des requêtes
                        .allowedHeaders("*"); // En-têtes HTTP autorisés
            }
        };
    }
}
