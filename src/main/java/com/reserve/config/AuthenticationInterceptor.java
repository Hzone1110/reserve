package com.reserve.config;

import com.reserve.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object object) throws Exception {
        System.out.println(request.getRequestURI());
        List<String> asList = Arrays.asList("/api/userLogin", "/api/adminLogin");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String uri = request.getRequestURI();
        if (asList.contains(uri)) {
            return true;
        }
        String token = TokenUtil.getToken(request);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if (token == null) {
            response.getWriter().println("{\"msg\":\"没有权限，请先登录\"}");
            response.setStatus(401);
            return false;
        }
        try {
            Map<String, String> map = TokenUtil.parseToken(token);
            long timeStamp = Long.parseLong(map.get("timeStamp"));
            if (timeStamp < System.currentTimeMillis()) {
                response.getWriter().println("{\"msg\":\"用户登陆凭证已过期\"}");
                response.setStatus(401);
                return false;
            }
        } catch (Exception exception) {
            response.getWriter().println("{\"msg\":\"权限校验失败\"}");
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
