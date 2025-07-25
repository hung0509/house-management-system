package com.muabanbds.identity_service.service.impl;

import com.muabanbds.common_service.config.ModelMapperConfig;
import com.muabanbds.common_service.dto.identityDto.request.PermissionRequest;
import com.muabanbds.common_service.dto.identityDto.response.PermissionResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import com.muabanbds.identity_service.entity.Permission;
import com.muabanbds.identity_service.repository.PermissionRepository;
import com.muabanbds.identity_service.service.PermissionService;
import com.muabanbds.identity_service.specification.PermissionSpecification;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    ModelMapper modelMapper;

    @Override
    public ApiResponsePagination<List<PermissionResponse>> findAll(PermissionRequest req) {
        log.info("***Log role service - save role***");
        log.info("{dto} :" + req);

        log.info("***Log transactions service - get all transaction by pagination***");
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getPageSize()
        );

        Specification<Permission> spec = PermissionSpecification.getSpecification(req);

        Page<Permission> query = permissionRepository.findAll(spec, pageable);

        List<Permission> permissions = query.getContent();
        return ApiResponsePagination.<List<PermissionResponse>>builder()
                .result(permissions.stream().map(item -> modelMapper.map(item, PermissionResponse.class)).toList())
                .totalPages(query.getTotalPages())
                .currentPage(req.getPage())
                .pageSize(req.getPageSize())
                .totalItems(query.getTotalElements())
                .build();
    }

    @Override
    public ApiResponse<PermissionResponse> findById(Integer integer) {
        return null;
    }

    @Override
    public ApiResponse<PermissionResponse> save(PermissionRequest req) {
        log.info("***Log permission service - save permission***");
        log.info("{dto} :" + req);
        Permission permission = modelMapper.map(req, Permission.class);

        permission = permissionRepository.save(permission);

        return ApiResponse.<PermissionResponse>builder()
                .result(modelMapper.map(permission, PermissionResponse.class))
                .build();
    }

    @Override
    public ApiResponse<PermissionResponse> update(Integer id, PermissionRequest req) {
        return null;
    }

    @Override
    public ApiResponse<String> deleteById(Integer integer) {
        return null;
    }
}
