package com.auth.config;

import com.common.minio.proper.MinioProper;
import com.common.minio.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Lilopop
 * @description:
 */
@Configuration
@EnableConfigurationProperties(MinioProper.class)
@ConditionalOnProperty(prefix = "starter",name = "enable",havingValue = "true",matchIfMissing = true)
public class MinioConfig {
    @Autowired
    private MinioProper proper;

    @Bean
    public MinioUtil getMinioUtils(){
        return new MinioUtil(proper);
    }
}
