package com.muabanbds.proxy_client.business.identity.service;

import com.muabanbds.common_service.dto.identityDto.request.RoleRequest;
import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "IDENTITY-SERVICE", contextId = "RoleClientService", path = "/identity-service/api/v1/roles")
public interface RoleClientService {
    @PostMapping
    ResponseEntity<ApiResponse<RoleResponse>> create(@RequestBody RoleRequest roleRequest);
}
