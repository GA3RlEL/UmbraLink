package com.umbra.umbralink;

import com.umbra.umbralink.repository.ConversationRepository;
import com.umbra.umbralink.repository.MessageRepository;
import com.umbra.umbralink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UmbralinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmbralinkApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http:localhost:4200")
                        .allowedMethods("*")
                        .allowedHeaders("Authorization", "Content-Type", "*");
            }
        };
    }


}
