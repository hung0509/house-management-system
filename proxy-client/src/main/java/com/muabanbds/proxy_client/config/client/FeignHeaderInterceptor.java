package com.muabanbds.proxy_client.config.client;

import com.muabanbds.common_service.helper.AuditContext;
import com.muabanbds.common_service.helper.AuditInfo;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignHeaderInterceptor implements RequestInterceptor {// Dùng cho outgoing request(đi ra)
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();

            // Vẫn đính token gửi vào các service khác
            String token = request.getHeader("Authorization");
            if (token != null) {
                requestTemplate.header("Authorization", token);
            }
        }

        // Thêm thông tin từ AuditContext
        AuditInfo auditInfo = AuditContext.getAuditInfo();
        if (auditInfo != null) {
            if (auditInfo.getUserId() != null) {
                requestTemplate.header("X-User-Id", String.valueOf(auditInfo.getUserId()));
            }
            if (auditInfo.getUsername() != null) {
                requestTemplate.header("X-Username", auditInfo.getUsername());
            }
        }
    }
}

