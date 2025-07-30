package com.muabanbds.proxy_client.business.identity.service;

import com.muabanbds.common_service.dto.identityDto.request.RoleRequest;
import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "IDENTITY-SERVICE", contextId = "RoleClientService", path = "/identity-service/api/v1/roles")
public interface RoleClientService {
    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest);

    @GetMapping
    ApiResponsePagination<List<RoleResponse>> getAll(@SpringQueryMap RoleRequest req);
}
