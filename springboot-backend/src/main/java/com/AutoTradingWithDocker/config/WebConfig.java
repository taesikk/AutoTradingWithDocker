package com.AutoTradingWithDocker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")   // 운영에서는 도메인 지정
                .allowedMethods("GET", "POST", "DELETE", "PATCH")
                .allowedHeaders("*");
    }
}
