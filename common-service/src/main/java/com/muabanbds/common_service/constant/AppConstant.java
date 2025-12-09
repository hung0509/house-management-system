package com.muabanbds.common_service.constant;

public abstract class AppConstant {
    public static final String[] URL_PUBLIC = {
            "/api/v1/auths",
            "/api/v1/roles",
            "/api/v1/permissions",
            "/api/v1/accounts",
    };

    public static final String[] GET_URL_PUBLIC = {
    };

    public static final String COLLECTION_FIRESTORE = "theme";

    public static final class DOCUMENT_FIRESTORE {
        public static final String DOCUMENT_MENU = "default";
        public static final String DOCUMENT_COMMON = "common";
        public static final String DOCUMENT_CONTENT_SECTION = "content-section";
        public static final String DOCUMENT_SEARCH_SECTION = "search_section";
    }

}
