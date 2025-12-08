package com.muabanbds.Property.Service.specification;

import com.muabanbds.Property.Service.entity.UnitCategory;
import com.muabanbds.common_service.dto.identityDto.request.RoleRequest;
import com.muabanbds.common_service.dto.propertyDto.request.UnitCategoryRequest;
import org.springframework.data.jpa.domain.Specification;

public class UnitCategorySpecification {

    public static Specification<UnitCategory> isName(String name)
    {
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("name"),  name );
    }

    public static Specification<UnitCategory> isRoleCode(String code)
    {
        return (root, query, criteriaBuilder) ->
                code == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("code"),  code );
    }
    public static Specification<UnitCategory> getSpecification(UnitCategoryRequest req) {
        return Specification.where(isName(req.getName()))
                        .and(isRoleCode(req.getCode()));
    }
}
