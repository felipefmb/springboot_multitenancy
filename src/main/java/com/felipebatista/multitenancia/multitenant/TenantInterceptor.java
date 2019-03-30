package com.felipebatista.multitenancia.multitenant;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

    private static final List<String> URLS_IGNORE = new ArrayList<>();
    static {
        URLS_IGNORE.add("/login");
        URLS_IGNORE.add("/register");
        URLS_IGNORE.add("/entidades");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if(URLS_IGNORE.contains(request.getRequestURI())) {
            return true;
        }

        String tenantId = request.getHeader("user-access");
        TenantContext.setCurrentTenant(tenantId);

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContext.clear();
    }
}
