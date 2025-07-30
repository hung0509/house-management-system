package com.muabanbds.proxy_client.business.identity.service;

import com.muabanbds.common_service.dto.identityDto.request.PermissionRequest;
import com.muabanbds.common_service.dto.identityDto.response.PermissionResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "IDENTITY-SERVICE", contextId = "PermissionClientService", path = "/identity-service/api/v1/permissions")
public interface PermissionClientService {
    @PostMapping
    ApiResponse<PermissionResponse> save(@RequestBody PermissionRequest req);

    @GetMapping
    ApiResponsePagination<List<PermissionResponse>> getAll(@SpringQueryMap PermissionRequest req);
}
