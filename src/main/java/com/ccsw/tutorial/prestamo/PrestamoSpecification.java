package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PrestamoSpecification {

    public static Specification<Prestamo> createSpecification(PrestamoSearchDto dto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getGameId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("game").get("id"), dto.getGameId()));
            }
            if (dto.getClientId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("client").get("id"), dto.getClientId()));
            }
            if (dto.getDate() != null) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), dto.getDate()),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), dto.getDate())
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
