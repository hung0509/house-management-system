package com.muabanbds.identity_service.repository;

import com.muabanbds.identity_service.entity.RolePermission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    @Transactional
    @Modifying
    @Query(value = "Update d_role_permission Set is_active = 'N' where d_role_id = :roleId and is_active ='Y'", nativeQuery = true)
    void cancelAllPermissionByRoleId(Integer roleId);

    RolePermission findByRoleIdAndPermissionId(Integer roleId, Integer permissionId);
}
