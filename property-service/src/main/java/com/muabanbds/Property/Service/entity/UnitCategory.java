package com.muabanbds.Property.Service.entity;

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
@Table(name = "d_unit_category")
public class UnitCategory extends CommonMappedEntity {
    @Id
    @Column(name = "d_unit_category_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_unit_category_id_sq")
    @SequenceGenerator(name = "d_unit_category_id_sq", sequenceName = "d_unit_category_id_sq", allocationSize = 1)
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    String status;

    @Column(name = "d_image_id")
    Integer imageId;
}
