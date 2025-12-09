package com.muabanbds.system_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.muabanbds.common_service.constant.AppConstant;
import com.muabanbds.common_service.dto.systemDto.request.ThemeMenuRequest;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.system_service.service.ThemeMenuService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThemeMeneServiceImpl implements ThemeMenuService {
    Firestore firestore;
    ObjectMapper objectMapper;

    private DocumentReference getMenuDocument() {
        return firestore.collection(AppConstant.COLLECTION_FIRESTORE).document(AppConstant.DOCUMENT_FIRESTORE.DOCUMENT_MENU);
    }

    // Lấy menu
    public List<ThemeMenuRequest> getMenus() {
        List<ThemeMenuRequest> result = new ArrayList<>();

        try {
            DocumentSnapshot doc = getMenuDocument().get().get();

            if (!doc.exists()) return new ArrayList<>();

            List<Map<String, Object>> rawList = (List<Map<String, Object>>) doc.get("menuItems");

            if (rawList == null || rawList.isEmpty())
                return new ArrayList<>();

            for (Map<String, Object> map : rawList) {
                ThemeMenuRequest dto = objectMapper.convertValue(map, ThemeMenuRequest.class);
                result.add(dto);
            }

            return result;
        }catch(Exception e){
            e.printStackTrace();
            log.error("Error occurred while processing request", e);
        }
        return result;
    }

    // Ghi đè toàn bộ menu
    public void updateMenu(List<ThemeMenuRequest> newMenu) {
        try {
            getMenuDocument().update("menuItems", newMenu).get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    // Thêm 1 item
//    public void addMenuItem(ThemeMenuRequest item) throws Exception {
//        List<ThemeMenuRequest> current = getMenus();
//        current.add(item);
//        updateMenu(current);
//    }
//
//    // Cập nhật 1 menu item
//    public void updateMenuItem(int index, ThemeMenuRequest updated) throws Exception {
//        List<ThemeMenuRequest> current = getMenus();
//        current.set(index, updated);
//        updateMenu(current);
//    }
//
//    // Xoá 1 item
//    public void deleteMenuItem(int index) throws Exception {
//        List<ThemeMenuRequest> current = getMenus();
//        current.remove(index);
//        updateMenu(current);
//    }

    @Override
    public ApiResponse<List<ThemeMenuRequest>> getMenu() {
        return ApiResponse.<List<ThemeMenuRequest>>builder()
                .code(HttpStatus.SC_OK)
                .result(getMenus())
                .build();
    }

    @Override
    public ApiResponse<Void> update(List<ThemeMenuRequest> themeMenuRequest) {
        updateMenu(themeMenuRequest);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.SC_OK)
                .build();
    }
}
