package com.backend.miniProjetBack;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow credentials (if needed)
        config.setAllowCredentials(true);

        // Add allowed origins
        config.addAllowedOrigin("http://localhost:4200");

        // Add allowed headers and methods
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Register the CORS configuration for all endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
