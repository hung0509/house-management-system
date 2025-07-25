package com.muabanbds.identity_service.resource;

import com.muabanbds.common_service.dto.identityDto.request.PermissionRequest;
import com.muabanbds.common_service.dto.identityDto.response.PermissionResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.identity_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/permissions")
public class PermissionResource {
    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest req) {
        log.info("***Log permission resource - save permission***");
        return permissionService.save(req);
    }
}
