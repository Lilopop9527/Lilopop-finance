package com.finance.feign.fallback;

import com.common.core.pojo.CommonData;
import com.finance.feign.UserCheckService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

/**
 * @author: Lilopop
 * @description:
 */
@Service
public class UserCheckFallbackFactory implements FallbackFactory {
    @Override
    public Object create(Throwable cause) {
        return new UserCheckService() {
            @Override
            public CommonData<Boolean> checkUser(Long id, String name) {
                return new CommonData<>(500,"调用checkUser接口失败",false);
            }
        };
    }
}
