package com.kidsnara.library.config;

import com.kidsnara.library.constant.EnumMapper;
import com.kidsnara.library.constant.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * bean 생성시 가장 먼저 실행
 */
@Order(1)
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public EnumMapper enumMapper(){
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("Role", Role.class);
        return enumMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("X-ACCESS-TOKEN");
    }
}
