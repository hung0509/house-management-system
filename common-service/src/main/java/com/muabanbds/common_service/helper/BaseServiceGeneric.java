package com.muabanbds.common_service.helper;

import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;

import java.util.List;

public interface BaseServiceGeneric<ID, R, Response> {
    ApiResponsePagination<List<Response>> findAll(R request);
    ApiResponse<Response> findById(ID id);
    ApiResponse<Response> save(R req);
    ApiResponse<Response> update(ID id, R req);
    ApiResponse<String> deleteById(ID id);
}

