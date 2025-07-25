package com.muabanbds.proxy_client.config.security;

import com.muabanbds.proxy_client.config.filter.UserDetailCustom;
import com.muabanbds.proxy_client.config.filter.UserDetailServiceCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailServiceCustom userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("***Authentication service. Custom authentication provider***");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        log.info("username:" + username + ", password: " + password);

        UserDetailCustom userDetails = (UserDetailCustom) userDetailsService.loadUserByUsername(username);

        log.info("UserDetails password: " + userDetails.getPassword());

        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials!");
        }
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}