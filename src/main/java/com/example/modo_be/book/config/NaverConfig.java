package com.example.modo_be.book.config;


import com.example.modo_be.book.request.NaverHeaderRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:naverSearchAPI.properties")
public class NaverConfig {

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Bean
    public NaverHeaderRequest toNaverHeaderRequest(){
        return new NaverHeaderRequest(this.getClientId(), this.getClientSecret());
    }
}
