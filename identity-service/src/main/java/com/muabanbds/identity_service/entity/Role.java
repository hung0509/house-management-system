package com.muabanbds.identity_service.entity;

import com.muabanbds.core_service.entity.CommonMappedEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "d_role")
public class Role extends CommonMappedEntity {
    @Id
    @Column(name = "d_role_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;

    @Column(name = "description")
    String description;
}
