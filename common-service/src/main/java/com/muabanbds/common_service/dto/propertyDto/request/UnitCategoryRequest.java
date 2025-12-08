package com.muabanbds.common_service.dto.propertyDto.request;

import com.muabanbds.common_service.dto.BaseQueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitCategoryRequest extends BaseQueryRequest {
    String name;
    String code;
    String description;
    String status;
    String urlImage;
}
