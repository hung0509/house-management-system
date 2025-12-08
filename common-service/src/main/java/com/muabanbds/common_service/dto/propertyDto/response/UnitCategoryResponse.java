package com.muabanbds.common_service.dto.propertyDto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitCategoryResponse {
    Integer id;
    String name;
    String code;
    String description;
    String status;
    String urlImage;
}
