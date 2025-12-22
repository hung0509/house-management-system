package com.muabanbds.identity_service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "d_user")
public class User extends CommonMappedEntity {
    @Id
    @Column(name = "d_user_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    Integer id;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "date_of_birth")
    Instant dateOfBirth;

    @Column(name = "gender")
    String gender;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "address")
    String address;

    @Column(name = "description")
    String description;
}
