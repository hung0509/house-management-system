package com.muabanbds.proxy_client.business.identity.service;

import com.muabanbds.common_service.dto.identityDto.request.AccountRequest;
import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "IDENTITY-SERVICE", contextId = "AccountClientService", path = "/identity-service/api/v1/accounts")
public interface AccountClientService {
    @GetMapping("/username/{username}")
    ApiResponse<AccountResponse> findByUsername(@PathVariable String username);

    @PostMapping
    ApiResponse<AccountResponse> create(@RequestBody AccountRequest accountRequest);
}
