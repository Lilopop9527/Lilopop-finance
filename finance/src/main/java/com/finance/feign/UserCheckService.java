package com.finance.feign;

import com.common.core.pojo.CommonData;
import com.finance.feign.fallback.UserCheckFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "security",path = "/feign",fallbackFactory = UserCheckFallbackFactory.class)
public interface UserCheckService {

    @GetMapping("/check")
    public CommonData<Boolean> checkUser(@RequestParam("id") Long id, @RequestParam("name") String name);
}
