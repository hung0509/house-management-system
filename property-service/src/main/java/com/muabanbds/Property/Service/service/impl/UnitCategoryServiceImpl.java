package com.muabanbds.Property.Service.service.impl;

import com.muabanbds.Property.Service.entity.UnitCategory;
import com.muabanbds.Property.Service.repository.UnitCategoryRepository;
import com.muabanbds.Property.Service.service.UnitCategoryService;
import com.muabanbds.Property.Service.specification.UnitCategorySpecification;
import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import com.muabanbds.common_service.dto.propertyDto.request.UnitCategoryRequest;
import com.muabanbds.common_service.dto.propertyDto.response.UnitCategoryResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class UnitCategoryServiceImpl implements UnitCategoryService {
    UnitCategoryRepository unitCategoryRepository;
    ModelMapper modelMapper;

    @Override
    public ApiResponsePagination<List<UnitCategoryResponse>> findAll(UnitCategoryRequest req) {
        log.info("***Log Unit Category service - get all Unit Category***");
        log.info("{dto} :" + req);
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getPageSize()
        );
        Specification<UnitCategory> specification = UnitCategorySpecification.getSpecification(req);

        Page<UnitCategory> query = unitCategoryRepository.findAll(specification, pageable);

        List<UnitCategoryResponse> data = query.getContent().stream()
                .map(item -> modelMapper.map(item, UnitCategoryResponse.class)).toList();

        return ApiResponsePagination.<List<UnitCategoryResponse>>builder()
                .result(data)
                .totalPages(query.getTotalPages())
                .currentPage(req.getPage())
                .pageSize(req.getPageSize())
                .totalItems(query.getTotalElements())
                .build();
    }

    @Override
    public ApiResponse<UnitCategoryResponse> findById(Integer integer) {
        return null;
    }

    @Override
    public ApiResponse<UnitCategoryResponse> save(UnitCategoryRequest req) {
        log.info("***Log Unit Category service - save Unit Category***");
        log.info("{req} : {}", req);

        UnitCategory unitCategory = modelMapper.map(req, UnitCategory.class);
        unitCategoryRepository.save(unitCategory);

        return ApiResponse.<UnitCategoryResponse>builder()
                .code(HttpStatus.SC_OK)
                .message("Unit Category Saved Successfully")
                .result(modelMapper.map(unitCategory, UnitCategoryResponse.class))
                .build();
    }

    @Override
    public ApiResponse<UnitCategoryResponse> update(Integer integer, UnitCategoryRequest req) {
        return null;
    }

    @Override
    public ApiResponse<String> deleteById(Integer integer) {
        return null;
    }
}
