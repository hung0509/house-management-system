package com.muabanbds.Property.Service.entity;

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
@Table(name = "d_building")
public class Building extends CommonMappedEntity {
    @Id
    @Column(name = "d_bulding_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_building_id_seq")
    @SequenceGenerator(name = "d_building_id_seq", sequenceName = "d_building_id_seq", allocationSize = 1)
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "address")
    String address;

    @Column(name = "d_user_id")
    Integer userId;

    @Column(name = "description")
    String description;
}
