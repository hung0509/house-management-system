package com.muabanbds.identity_service.entity;

import com.muabanbds.core_service.entity.CommonMappedEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "d_role_permission")
@Builder
public class RolePermission extends CommonMappedEntity {
    @Id
    @Column(name = "d_role_permission_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_permission_id_seq")
    @SequenceGenerator(name = "role_permission_id_seq", sequenceName = "role_permission_id_seq", allocationSize = 1)
    Integer id;

    @Column(name = "d_role_id")
    Integer roleId;

    @Column(name = "d_permission_id")
    Integer permissionId;
}
