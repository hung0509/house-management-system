package com.muabanbds.identity_service.specification;

import com.muabanbds.common_service.dto.identityDto.request.RoleRequest;
import com.muabanbds.identity_service.entity.Role;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RoleSpecification {
    public static Specification<Role> isRoleId(Integer id)
    {
        return (root, query, criteriaBuilder) ->
                id == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("id"),  id );
    }

    public static Specification<Role> isName(String name)
    {
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("name"),  name );
    }

    public static Specification<Role> isRoleCode(String code)
    {
        return (root, query, criteriaBuilder) ->
                code == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("code"),  code );
    }

//    public static Specification<TransactionView> isBalance(BigDecimal fromBalance, BigDecimal toBalance)
//    {
//        if(fromBalance != null && toBalance != null){
//            return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("balance"), fromBalance, toBalance);
//        }
//
//        if(fromBalance == null && toBalance == null){
//            return (root, query, criteriaBuilder) ->  criteriaBuilder.conjunction();
//        }
//        if(fromBalance == null){
//            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("balance"), fromBalance);
//        }
//
//        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("balance"), toBalance);
//    }


    public static Specification<Role> getSpecification(RoleRequest req) {
        return Specification.where(isRoleId(req.getId()))
                .and(isName(req.getName())
                .and(isRoleCode(req.getCode())));
    }
}
