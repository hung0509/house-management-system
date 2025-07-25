package com.muabanbds.common_service.helper;

public class AuditContext {
    private static final ThreadLocal<AuditInfo> auditInfoHolder = new ThreadLocal<>();

    public static void setAuditInfo(AuditInfo auditInfo) {
        auditInfoHolder.set(auditInfo);
    }

    public static AuditInfo getAuditInfo() {
        return auditInfoHolder.get();
    }

    public static void clear() {
        auditInfoHolder.remove(); // tránh memory leak
    }
}
