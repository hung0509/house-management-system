package com.muabanbds.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "d_invalidated_token")
public class InvalidatedToken extends CommonMappedEntity{
    @Id
    @Column(name = "d_invalidated_token_id", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invalidated_token_id_seq")
    @SequenceGenerator(name = "invalidated_token_id_seq", sequenceName = "invalidated_token_id_seq", allocationSize = 1)
    Integer id;

    @Column(name = "token_id")
    String tokenId;

    @Column(name = "expire_date")
    Instant expireDate;
}
