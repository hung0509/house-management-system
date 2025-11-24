package com.muabanbds.identity_service.service.impl;

import com.muabanbds.common_service.dto.identityDto.request.RoleRequest;
import com.muabanbds.common_service.dto.identityDto.response.PermissionResponse;
import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import com.muabanbds.core_service.exception.AppException;
import com.muabanbds.core_service.exception.ErrorCode;
import com.muabanbds.common_service.helper.ParseHelper;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import com.muabanbds.identity_service.entity.Permission;
import com.muabanbds.identity_service.entity.Role;
import com.muabanbds.identity_service.entity.RolePermission;
import com.muabanbds.identity_service.repository.PermissionRepository;
import com.muabanbds.identity_service.repository.RolePermissionRepository;
import com.muabanbds.identity_service.repository.RoleRepository;
import com.muabanbds.identity_service.service.RoleService;
import com.muabanbds.identity_service.specification.RoleSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RolePermissionRepository rolePermissionRepository;
    PermissionRepository permissionRepository;

    ModelMapper modelMapper;
    JdbcTemplate jdbcTemplate;

    @Override
    public ApiResponsePagination<List<RoleResponse>> findAll(RoleRequest req) {
        log.info("***Log role service - save role***");
        log.info("{dto} :" + req);
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getPageSize()
        );
        Specification<Role> specification = RoleSpecification.getSpecification(req);

        Page<Role> query = roleRepository.findAll(specification, pageable);

        List<RoleResponse> data = query.getContent().stream()
                .map(item -> modelMapper.map(item, RoleResponse.class)).toList();

        if(!data.isEmpty()){
            for(RoleResponse role: data){
                role.setPermissions(getPermission(role.getId()));
            }
        }
        return ApiResponsePagination.<List<RoleResponse>>builder()
                .result(data)
                .totalPages(query.getTotalPages())
                .currentPage(req.getPage())
                .pageSize(req.getPageSize())
                .totalItems(query.getTotalElements())
                .build();
    }

    @Override
    public ApiResponse<RoleResponse> findById(Integer integer) {
        return null;
    }

    @Override
    @Transactional
    public ApiResponse<RoleResponse> save(RoleRequest req) {
        log.info("***Log role service - save role***");
        log.info("{dto} :" + req);

        Role role = modelMapper.map(req, Role.class);
        role = roleRepository.save(role);

        List<PermissionResponse> permissionResponses = new ArrayList<>();
        if(req.getPermissions() != null && !req.getPermissions().isEmpty()) {
            List<Permission> permissions = permissionRepository.findPermissionsByCodes(req.getPermissions());

            if (permissions.size() != req.getPermissions().size()) {
                throw new AppException(ErrorCode.PERMISSION_INVALID);
            }

            List<RolePermission> rolePermissions = new ArrayList<>();

            for(Permission permission : permissions) {
                RolePermission rolePermission = RolePermission.builder()
                        .roleId(role.getId())
                        .permissionId(permission.getId())
                        .build();
                rolePermissions.add(rolePermission);
            }
            rolePermissionRepository.saveAll(rolePermissions);

            permissionResponses = permissions.stream()
                    .map(item -> modelMapper.map(item, PermissionResponse.class)).toList();
        }

        RoleResponse roleResponse = modelMapper.map(role, RoleResponse.class);
        roleResponse.setPermissions(permissionResponses);

        return ApiResponse.<RoleResponse>builder()
                .result(roleResponse)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<RoleResponse> update(Integer id, RoleRequest req) {
        log.info("***Log role service - update role***");
        log.info("{dto} :" + req);

        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXIST));
        modelMapper.map(req, role);

        List<PermissionResponse> permissionResponses = new ArrayList<>();
        if(req.getPermissions() != null) {
            List<Permission> permissions = permissionRepository.findPermissionsByCodes(req.getPermissions());

            if (permissions.size() != req.getPermissions().size()) {
                throw new AppException(ErrorCode.PERMISSION_INVALID);
            }

            rolePermissionRepository.cancelAllPermissionByRoleId(role.getId());
            List<RolePermission> rolePermissions = new ArrayList<>();
            for(Permission permission : permissions) {
                RolePermission rolePermission = rolePermissionRepository
                        .findByRoleIdAndPermissionId(role.getId(), permission.getId());

                if(rolePermission == null) {
                    rolePermission = RolePermission.builder()
                            .roleId(role.getId())
                            .permissionId(permission.getId())
                            .build();
                    rolePermission.setIsActive("Y");

                    rolePermissions.add(rolePermission);
                }
            }
            rolePermissionRepository.saveAll(rolePermissions);

            permissionResponses = permissions.stream()
                    .map(item -> modelMapper.map(item, PermissionResponse.class)).toList();
        }

        RoleResponse roleResponse = modelMapper.map(role, RoleResponse.class);
        roleResponse.setPermissions(permissionResponses);

        return ApiResponse.<RoleResponse>builder()
                .result(roleResponse)
                .build();
    }

    @Override
    public ApiResponse<String> deleteById(Integer integer) {
        return null;
    }

    private List<PermissionResponse> getPermission(Integer roleId){
        StringBuilder sql = new StringBuilder("SELECT * FROM d_role_permission_v where d_role_id = ?");

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql.toString(), roleId);

        List<PermissionResponse> permissionResponses = new ArrayList<>();
        if(!results.isEmpty()){
            for(Map<String, Object> rs: results){
                PermissionResponse permissionResponse = PermissionResponse.builder()
                        .id(ParseHelper.INT.parse(rs.get("d_permission_id")))
                        .code(ParseHelper.STRING.parse(rs.get("code")))
                        .name(ParseHelper.STRING.parse(rs.get("name")))
                        .build();
                permissionResponses.add(permissionResponse);
            }
        }

        return permissionResponses;
    }
}
