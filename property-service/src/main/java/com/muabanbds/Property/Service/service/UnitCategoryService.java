package com.muabanbds.Property.Service.service;

import com.muabanbds.common_service.dto.propertyDto.request.UnitCategoryRequest;
import com.muabanbds.common_service.dto.propertyDto.response.UnitCategoryResponse;
import com.muabanbds.common_service.helper.BaseServiceGeneric;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitCategoryService extends BaseServiceGeneric<Integer, UnitCategoryRequest, UnitCategoryResponse> {
}
