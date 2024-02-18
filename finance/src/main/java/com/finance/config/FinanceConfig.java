package com.finance.config;

import com.common.core.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Lilopop
 * @description:
 */
@Configuration
public class FinanceConfig {
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}
