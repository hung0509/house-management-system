package com.muabanbds.system_service.service;

import com.muabanbds.common_service.dto.systemDto.request.FirestoreDocumentRequest;

import java.util.List;

public interface FirestoreDocumentService {
    <T> List<T> readList(String collection, String document, String field, Class<T> clazz);

    <T> void writeList(String collection, String document, String field, List<T> data);

    void updateListByJson(FirestoreDocumentRequest req);
}
