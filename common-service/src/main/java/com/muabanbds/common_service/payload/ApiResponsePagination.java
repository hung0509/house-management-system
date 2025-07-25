package com.muabanbds.common_service.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponsePagination<T> {
    int code = 0;
    String message = "Successfully";
    T result;
    Integer totalPages;
    Integer currentPage;
    Integer pageSize;
    Long totalItems;
}
