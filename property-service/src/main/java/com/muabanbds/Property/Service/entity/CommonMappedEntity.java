package com.muabanbds.Property.Service.entity;

import com.muabanbds.common_service.helper.AuditContext;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public class CommonMappedEntity {
    @Column(name = "is_active", columnDefinition = "varchar(1) default 'Y'")
    String isActive = "Y";

    @Column(name = "created_at",updatable = false)
    Instant createdAt;

    @Column(name = "created_by")
    Integer createdBy;

    @Column(name = "updated_at")
    Instant updatedAt;

    @Column(name = "updated_by")
    Integer updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.updatedBy = AuditContext.getAuditInfo().getUserId();
        this.createdBy = AuditContext.getAuditInfo().getUserId();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = AuditContext.getAuditInfo().getUserId();
    }
}
