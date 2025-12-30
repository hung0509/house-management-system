package com.muabanbds.common_service.dto.propertyDto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitRequest {
    Integer buildingId;
    Integer unitCategoryId;
    String code;
    String description;
    String block;
    Integer floor;
    Integer bedrooms;
    Integer bathrooms;
    Integer balconies;
    String direction;
    String viewType;
    String furnishedStatus;
    String furnitureNote;
}
