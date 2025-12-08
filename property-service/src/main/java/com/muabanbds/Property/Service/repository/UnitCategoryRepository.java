package com.muabanbds.Property.Service.repository;

import com.muabanbds.Property.Service.entity.UnitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitCategoryRepository extends JpaRepository<UnitCategory, Integer>, JpaSpecificationExecutor<UnitCategory> {
}
