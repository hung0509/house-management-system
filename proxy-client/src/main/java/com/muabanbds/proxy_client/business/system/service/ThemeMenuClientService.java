package com.muabanbds.proxy_client.business.system.service;


import com.muabanbds.common_service.dto.systemDto.request.ThemeMenuRequest;
import com.muabanbds.common_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "SYSTEM-SERVICE", contextId = "ThemeMenuClientService", path = "/system-service/api/v1/themes/menu")
public interface ThemeMenuClientService {
    @GetMapping
    ApiResponse<List<ThemeMenuRequest>> getMenu();

    @PutMapping
    ApiResponse<Void> update(@RequestBody List<ThemeMenuRequest> themeMenuRequests);
}
