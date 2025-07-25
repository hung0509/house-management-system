package com.muabanbds.identity_service.repository;

import com.muabanbds.identity_service.entity.AccountRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {
    @Transactional
    @Modifying
    @Query(value = "Update d_account_role Set is_active = 'N' where d_account_id = :accountId and is_active ='Y'", nativeQuery = true)
    void cancelAllRoleByAccountId(Integer accountId);

    AccountRole findByAccountIdAndRoleId(Integer accountId, Integer roleId);
}
