package com.muabanbds.identity_service.repository;

import com.muabanbds.identity_service.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, Integer> {
    Boolean existsByTokenId(String tokenId);
}
