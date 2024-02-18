package com.finance.config;

import com.common.interceptor.handle.TokenInterceptor;
import com.common.interceptor.section.RoleSection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Lilopop
 * @description:
 */
@Configuration
public class InterceptConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/feign/*");
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
