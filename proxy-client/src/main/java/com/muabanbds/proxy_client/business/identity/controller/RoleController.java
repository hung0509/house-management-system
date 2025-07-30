package com.muabanbds.proxy_client.business.identity.controller;

import com.muabanbds.common_service.dto.identityDto.request.RoleRequest;
import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import com.muabanbds.proxy_client.business.identity.service.RoleClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleController {
    RoleClientService clientService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest req) {
        log.info("*** Save, controller; save role  ***");
        return this.clientService.create(req);
    }

    @GetMapping
    public ApiResponsePagination<List<RoleResponse>> getAll(@SpringQueryMap RoleRequest req){
        log.info("*** Save, controller; get all role  ***");
        return this.clientService.getAll(req);
    }
}
