package com.stream.config;

import com.common.core.pojo.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class LogStreamConfig {

    Logger logger = LoggerFactory.getLogger(LogStreamConfig.class);

    @Bean
    Consumer<List<LogMessage>> logConsumer(){
        return list->{
            list.forEach(e->{
                StringBuilder bu = new StringBuilder(e.getFrom());
                bu.append("服务").append(e.getMethod())
                        .append("参数:").append(e.getData())
                        .append("，执行结果为:");
                switch (e.getStatus()) {
                    case 0 -> {
                        bu.append("调用");
                        logger.info(bu.toString());
                    }
                    case 1 -> {
                        bu.append("成功");
                        logger.info(bu.toString());
                    }
                    case 2 -> {
                        bu.append("失败");
                        logger.error(bu.toString());
                    }
                }
            });
        };
    }
}
