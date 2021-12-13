package com.danjinae.web.Configuration;

import java.util.Arrays;
import java.util.List;

import com.danjinae.web.HttpRequest.loginService.LoginCheckIntercepter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.java.Log;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer  {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> URL_PATTERNS = Arrays.asList("/**");
        registry.addInterceptor(LoginInterceptor()).addPathPatterns(URL_PATTERNS).excludePathPatterns("/join/**" ,"/login/**", "/testcookie");
    }
    
    @Bean
    public LoginCheckIntercepter LoginInterceptor() {
        return new LoginCheckIntercepter();
    }

}
