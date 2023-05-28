package com.example.modo_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ModoBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModoBeApplication.class, args);
    }

}
