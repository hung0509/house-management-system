package com.muabanbds.identity_service.config;

import com.muabanbds.common_service.helper.AuditContext;
import com.muabanbds.common_service.helper.AuditInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class HeaderExtractFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Integer userId = Optional.ofNullable(request.getHeader("userId")).map(Integer::valueOf).orElse(0);
        String username =Optional.ofNullable(request.getHeader("X-Username")).orElse("0");


        AuditContext.setAuditInfo(AuditInfo.builder()
                        .userId(userId)
                        .username(username)
                .build());

        try {
            filterChain.doFilter(request, response);
        } finally {
            AuditContext.clear(); // đảm bảo không rò rỉ context giữa các request
        }
    }

}


