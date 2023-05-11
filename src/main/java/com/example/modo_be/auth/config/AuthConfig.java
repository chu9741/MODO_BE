package com.example.modo_be.auth.config;

import com.example.modo_be.auth.component.BearerAuthInterceptor;
import com.example.modo_be.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final TokenService tokenService;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new BearerAuthInterceptor(tokenService))
                .addPathPatterns("/**")
                .excludePathPatterns("/signin","/signup","/","/docs/**");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*");

    }

}
