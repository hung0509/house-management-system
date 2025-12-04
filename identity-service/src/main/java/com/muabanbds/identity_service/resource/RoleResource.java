package com.muabanbds.identity_service.resource;

import com.muabanbds.common_service.dto.identityDto.request.RoleRequest;
import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import com.muabanbds.identity_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/roles")
public class RoleResource {
    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest req){
        log.info("***Log role resource - save role***");
        return roleService.save(req);
    }

    @GetMapping
    public ApiResponsePagination<List<RoleResponse>> getAllRoles(@ModelAttribute RoleRequest req){
        log.info("***Log role resource - get all roles***");
        return roleService.findAll(req);
    }
}
