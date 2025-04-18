package com.umbra.umbralink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class UmbralinkApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"));
        SpringApplication.run(UmbralinkApplication.class, args);
    }

}
