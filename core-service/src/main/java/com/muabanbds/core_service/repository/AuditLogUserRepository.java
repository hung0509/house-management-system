package com.muabanbds.core_service.repository;

import com.muabanbds.core_service.entity.AuditLogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogUserRepository extends JpaRepository<AuditLogUser, Integer> {
}
