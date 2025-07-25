package com.muabanbds.proxy_client.business.identity.service;

import com.muabanbds.common_service.dto.identityDto.request.InvalidatedTokenRequest;
import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.dto.identityDto.response.InvalidatedTokenResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "IDENTITY-SERVICE", contextId = "InvalidatedTokenClientService", path = "/identity-service/api/v1/invalidated")
public interface InvalidatedTokenClientService {
    @PostMapping("/token")
    ResponseEntity<ApiResponse<InvalidatedTokenResponse>> checkToken(@RequestBody InvalidatedTokenRequest req);
}
