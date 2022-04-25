package com.reserve.config;

import com.reserve.util.TokenUtil;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final TokenUtil tokenUtil;

    public AuthenticationInterceptor(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        List<String> asList = Arrays.asList("/api/userLogin", "/api/adminLogin");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String uri = request.getRequestURI();
        if (asList.contains(uri)) {
            return true;
        }
        String token = tokenUtil.getToken(request);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if (token == null) {
            response.getWriter().println("{\"msg\":\"没有权限，请先登录\"}");
            response.setStatus(401);
            return false;
        }
        try {
            Map<String, String> map = tokenUtil.parseToken(token);
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
