package com.muabanbds.proxy_client.business.identity.service;

import com.muabanbds.common_service.dto.identityDto.request.AuthenticationRequest;
import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.dto.identityDto.response.AuthenticationResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.exception.AppException;
import com.muabanbds.common_service.exception.ErrorCode;
import com.muabanbds.proxy_client.jwt.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationService {
    AuthenticationManager authenticationManager;
    AccountClientService accountClientService;
    JwtUtil jwtUtil;

    public ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest req) {
        log.info("***Log account service - Authentication***");
        log.info("{req} :" + req);

        log.info("***Log authentication service - authenticate account***");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        log.info("User Detail: " + SecurityContextHolder.getContext().getAuthentication());
        ApiResponse<AccountResponse> account = accountClientService.findByUsername(req.getUsername());

        if(account.getCode() == 0 && account.getResult() != null ){
            String token = this.jwtUtil.generateToken(account.getResult());

            return ApiResponse.<AuthenticationResponse>builder()
                    .result(AuthenticationResponse.builder()
                            .token(token)
                            .build())
                    .build();
        }else
            throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}
