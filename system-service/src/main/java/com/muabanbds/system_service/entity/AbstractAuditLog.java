package com.muabanbds.system_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@SuperBuilder
abstract public class AbstractAuditLog implements Serializable {
    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "d_user_id")
    private Integer userId;
}
