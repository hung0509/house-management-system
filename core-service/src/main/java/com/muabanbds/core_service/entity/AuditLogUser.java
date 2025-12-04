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
@Table(name = "d_audit_log_user", schema = "pos")
public class AuditLogUser extends AbstractAuditLog implements Serializable {

    @Id
    @Column(name = "d_audit_log_user_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_audit_log_user_sq")
    @SequenceGenerator(name = "d_audit_log_user_sq", sequenceName = "d_audit_log_user_sq", allocationSize = 1)
    private Integer id;

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    @Column(name = "description")
    private String description;

    @Column(name = "d_audit_log_user_uu", columnDefinition = "UUID DEFAULT uuid_generate_v4()", insertable = false, updatable = false)
    private UUID uuid;

}