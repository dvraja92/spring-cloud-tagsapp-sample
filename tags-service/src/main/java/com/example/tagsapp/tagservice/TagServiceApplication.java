package com.example.tagsapp.tagservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableEurekaClient
public class TagServiceApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(TagServiceApplication.class, args);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .ignoreAcceptHeader(true)
            .defaultContentType(MediaType.APPLICATION_JSON);
    }
}
