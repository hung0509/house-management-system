package com.muabanbds.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "d_account_role")
@Builder
public class AccountRole extends CommonMappedEntity {
    @Id
    @Column(name = "d_account_role_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_role_id_seq")
    @SequenceGenerator(name = "account_role_id_seq", sequenceName = "account_role_id_seq", allocationSize = 1)
    Integer id;

    @Column(name = "d_account_id")
    Integer accountId;

    @Column(name = "d_role_id")
    Integer roleId;
}
