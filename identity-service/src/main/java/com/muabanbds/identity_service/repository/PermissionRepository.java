package com.muabanbds.identity_service.repository;

import com.muabanbds.identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer>, JpaSpecificationExecutor<Permission> {
    @Query("SELECT p FROM Permission p WHERE p.code IN :names")
    List<Permission> findPermissionsByCodes(@Param("names") List<String> names);
}
