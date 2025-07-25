package com.muabanbds.identity_service.repository;

import com.muabanbds.identity_service.entity.Permission;
import com.muabanbds.identity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    @Query("SELECT p FROM Role p WHERE p.name IN :names")
    List<Role> findRolesByNames(@Param("names") List<Integer> names);
}
