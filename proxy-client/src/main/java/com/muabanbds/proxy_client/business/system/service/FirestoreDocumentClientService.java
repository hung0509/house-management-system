package com.muabanbds.proxy_client.business.system.service;

import com.muabanbds.common_service.dto.systemDto.request.FirestoreDocumentRequest;
import com.muabanbds.common_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SYSTEM-SERVICE", contextId = "FireStoreClientService", path = "/system-service/api/v1/themes")
public interface FirestoreDocumentClientService {
    @PostMapping
    ApiResponse<Void> update(@RequestBody FirestoreDocumentRequest req);
}
