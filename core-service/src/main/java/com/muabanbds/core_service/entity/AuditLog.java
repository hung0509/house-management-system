package com.muabanbds.core_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "d_audit_log", schema = "pos")
public class AuditLog extends AbstractAuditLog implements Serializable {

    @Id
    @Column(name = "d_audit_log_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_audit_log_sq")
    @SequenceGenerator(name = "d_audit_log_sq", sequenceName = "d_audit_log_sq", allocationSize = 1)
    private Integer id;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "d_audit_log_uu", columnDefinition = "UUID DEFAULT uuid_generate_v4()", insertable = false, updatable = false)
    private UUID uuid;

}
