package com.muabanbds.proxy_client.business.identity.controller;

import com.muabanbds.common_service.dto.identityDto.request.PermissionRequest;
import com.muabanbds.common_service.dto.identityDto.response.PermissionResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.proxy_client.business.identity.service.PermissionClientService;
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
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PermissionController {
    PermissionClientService entityClientService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> save(@RequestBody PermissionRequest req) {
        log.info("*** Save, controller; save permission  ***");
        return ResponseEntity.ok(this.entityClientService.save(req).getBody());
    }
}
