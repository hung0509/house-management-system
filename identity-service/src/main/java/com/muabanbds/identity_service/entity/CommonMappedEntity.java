package com.muabanbds.identity_service.entity;

import com.muabanbds.common_service.helper.AuditContext;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class CommonMappedEntity {
    @Column(name = "is_active", columnDefinition = "varchar(1) default 'Y'", insertable = false)
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
