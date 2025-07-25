package com.muabanbds.identity_service.specification;

import com.muabanbds.common_service.dto.identityDto.request.PermissionRequest;
import com.muabanbds.identity_service.entity.Permission;
import org.springframework.data.jpa.domain.Specification;

public class PermissionSpecification {
    public static Specification<Permission> isPermissionId(Integer id){
        return (root, query, criteriaBuilder) ->
                id == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("id"),  id );
    }

    public static Specification<Permission> isPermissionName(String name){
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")) ,"%"+name.toLowerCase()+"%" );
    }

    public static Specification<Permission> isPermissionCode(String code){
        return (root, query, criteriaBuilder) ->
                code == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")) ,"%"+ code.toLowerCase() + "%" );
    }

    public static Specification<Permission> getSpecification(PermissionRequest req) {
        return Specification.where(isPermissionId(req.getId()))
                    .and(isPermissionName(req.getName())
                    .and(isPermissionCode(req.getCode())));
    }
}
