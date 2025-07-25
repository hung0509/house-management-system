package com.muabanbds.proxy_client.business.identity.controller;

import com.muabanbds.common_service.dto.identityDto.request.RoleRequest;
import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.proxy_client.business.identity.service.RoleClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleController {
    RoleClientService clientService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> create(@RequestBody RoleRequest req) {
        log.info("*** Save, controller; save role  ***");
        return ResponseEntity.ok(this.clientService.create(req).getBody());
    }
}
