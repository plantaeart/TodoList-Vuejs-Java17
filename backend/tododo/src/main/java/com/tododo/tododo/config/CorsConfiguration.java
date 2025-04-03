package com.tododo.tododo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return (WebMvcConfigurer) new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nullable CorsRegistry registry) {
                if (registry != null) {
                    registry.addMapping("/**")
                            .allowedOriginPatterns("*")
                            .allowCredentials(true)
                            .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS")
                            .allowedHeaders("*"); // Allow all headers
                }
            }
        };
    }

}