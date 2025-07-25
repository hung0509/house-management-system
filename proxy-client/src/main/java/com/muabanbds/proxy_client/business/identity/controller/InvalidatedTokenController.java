package com.muabanbds.proxy_client.business.identity.controller;

import com.muabanbds.common_service.dto.identityDto.request.InvalidatedTokenRequest;
import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.dto.identityDto.response.InvalidatedTokenResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.proxy_client.business.identity.service.InvalidatedTokenClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invalidated")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InvalidatedTokenController {
    InvalidatedTokenClientService clientService;

    @PostMapping("/token")
    ResponseEntity<ApiResponse<InvalidatedTokenResponse>> checkToken(@RequestBody InvalidatedTokenRequest req){
        return ResponseEntity.ok(this.clientService.checkToken(req).getBody());
    }
}
