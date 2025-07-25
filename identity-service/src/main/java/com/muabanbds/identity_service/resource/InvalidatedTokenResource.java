package com.muabanbds.identity_service.resource;

import com.muabanbds.common_service.dto.identityDto.request.InvalidatedTokenRequest;
import com.muabanbds.common_service.dto.identityDto.response.InvalidatedTokenResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.identity_service.service.InvalidatedTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/invalidated")
public class InvalidatedTokenResource {
    InvalidatedTokenService invalidatedTokenService;

    @PostMapping("/token")
    public ApiResponse<InvalidatedTokenResponse> invalidateToken(@RequestBody InvalidatedTokenRequest req) {
        log.info("***Log Invalidated Token resource - check token***");
        return invalidatedTokenService.invalidate(req);
    }
}
