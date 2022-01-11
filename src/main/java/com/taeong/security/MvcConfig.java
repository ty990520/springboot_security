package com.taeong.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //경로 - 뷰 매핑
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main.xhtml");
        registry.addViewController("/login").setViewName("login.xhtml");
        registry.addViewController("/admin").setViewName("admin.xhtml");
        registry.addViewController("/signup").setViewName("signup.xhtml");
    }
}
