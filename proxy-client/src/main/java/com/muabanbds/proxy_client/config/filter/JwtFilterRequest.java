package com.muabanbds.proxy_client.config.filter;

import com.muabanbds.common_service.constant.AppConstant;
import com.muabanbds.common_service.helper.AuditContext;
import com.muabanbds.common_service.helper.AuditInfo;
import com.muabanbds.proxy_client.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilterRequest extends OncePerRequestFilter { // Dùng cho incoming request(đi vào)
    private final UserDetailServiceCustom userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("--- 🛡️ Filter JWT authentication ---");
        log.info("🔹 Request URI: {}", request.getRequestURI());
        AntPathMatcher pathMatcher = new AntPathMatcher();

        try {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                log.info("🔸 OPTIONS request - skipping filter");
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            if (request.getMethod().equalsIgnoreCase("GET")) {
                for (String publicUrl : AppConstant.GET_URL_PUBLIC) {
                    if (pathMatcher.match("/app/api/v1" + publicUrl, request.getRequestURI())) {
                        log.info("🔓 GET public URL matched: {}", "/app" + publicUrl);
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            }

            if (request.getMethod().equalsIgnoreCase("POST")) {
                for (String publicUrl : AppConstant.URL_PUBLIC) {
                    if (request.getRequestURI().equalsIgnoreCase(publicUrl)) {
                        log.info("🔓 POST public URL matched: {}", "/app" + publicUrl);
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            }

            log.info("🔐 JWT filter - checking authorization header");
            final var authorizationHeader = request.getHeader("Authorization");
            log.info("Authorization header: {}", authorizationHeader);

            String username = null;
            String jwt = null;
            Integer userId = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                try {
                    username = jwtUtil.extractUsername(jwt);
                    userId = jwtUtil.extractUserID(jwt);
                    log.info("✅ JWT valid: username={}, userId={}", username, userId);
                    AuditContext.setAuditInfo(AuditInfo.builder()
                            .userId(userId)
                            .username(username)
                            .build());
                } catch (ExpiredJwtException e) {
                    log.error("❌ Token expired", e);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                    return;
                } catch (Exception e) {
                    log.error("❌ Invalid token", e);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            } else {
                log.warn("⚠️ Missing or invalid Authorization header");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.info("🔍 Validating token with UserDetails...");
                UserDetailCustom userDetails = (UserDetailCustom) this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    log.info("✅ Token validation successful. Setting authentication...");
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    log.error("❌ Token validation failed");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation failed");
                    return;
                }
            }

            log.info("➡️ Passing request to next filter (maybe controller)");
            filterChain.doFilter(request, response);

        } finally {
            AuditContext.clear(); //Dọn sạch ThreadLocal để tránh memory leak
            log.info("🧹 AuditContext cleared");
        }
    }




}
