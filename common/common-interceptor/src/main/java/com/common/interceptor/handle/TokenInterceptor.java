package com.common.interceptor.handle;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.interfaces.Claim;
import com.common.core.utils.JWTUtil;
import com.common.interceptor.config.ThreadInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.spi.TransactionalWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final String AUTH = "Auth";
    /**
     * 在请求前调用
     * @param request request请求
     * @param response response请求
     * @param handler
     * @return 验证token是否正确 true正确 false不正确
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTH);
        if(!JWTUtil.verifyToken(token)){
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        Map<String, Claim> claims = JWTUtil.getClaims(token);
        Claim claim = claims.get("role");
        Claim claim1 = claims.get("id");
        if (ObjectUtil.isNotNull(claim)&&ObjectUtil.isNotNull(claim1)){
            Integer roles = claim.asInt();
            Map<String,Object> map = new HashMap<>();
            map.put("role",roles);
            map.put("id",Long.parseLong(claim1.asString()));
            ThreadInfo.set(map);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadInfo.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
