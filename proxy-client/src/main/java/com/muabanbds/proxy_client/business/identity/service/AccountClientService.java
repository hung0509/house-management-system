package com.muabanbds.proxy_client.business.identity.service;

import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "IDENTITY-SERVICE", contextId = "AccountClientService", path = "/identity-service/api/v1/accounts")
public interface AccountClientService {
    @GetMapping("/username/{username}")
    ResponseEntity<ApiResponse<AccountResponse>> findByUsername(@PathVariable String username);
}
