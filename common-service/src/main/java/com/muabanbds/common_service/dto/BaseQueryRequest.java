package com.muabanbds.common_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseQueryRequest {
    private int page=0;
    private int pageSize=100;
    private String order="desc";
    private String sortBy="created";
}