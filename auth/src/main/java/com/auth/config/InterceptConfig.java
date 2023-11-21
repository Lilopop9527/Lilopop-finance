package com.auth.config;

import com.common.interceptor.handle.TokenInterceptor;
import com.common.interceptor.section.RoleSection;
import com.common.minio.config.MinioConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login","/user/saveUser","/error");
    }

    @Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    @Bean
    public RoleSection roleSection(){
        return new RoleSection();
    }

}
