package com.muabanbds.Property.Service.service.impl;

import com.muabanbds.Property.Service.repository.BuildingRepository;
import com.muabanbds.Property.Service.service.BuildingService;
import com.muabanbds.common_service.dto.identityDto.request.BuildingRequest;
import com.muabanbds.common_service.dto.identityDto.response.BuildingResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class BuildingServiceImpl  implements BuildingService {
    BuildingRepository buildingRepository;

    @Override
    public ApiResponsePagination<List<BuildingResponse>> findAll(BuildingRequest request) {
        return null;
    }

    @Override
    public ApiResponse<BuildingResponse> findById(Integer integer) {
        return null;
    }

    @Override
    public ApiResponse<BuildingResponse> save(BuildingRequest req) {
        return null;
    }

    @Override
    public ApiResponse<BuildingResponse> update(Integer integer, BuildingRequest req) {
        return null;
    }

    @Override
    public ApiResponse<String> deleteById(Integer integer) {
        return null;
    }
}
