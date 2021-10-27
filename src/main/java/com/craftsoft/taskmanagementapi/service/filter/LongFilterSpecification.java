package com.craftsoft.taskmanagementapi.service.filter;

import com.craftsoft.taskmanagementapi.domain.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class LongFilterSpecification implements Specification<Task> {

    private String column;
    private Long value;

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (value != null) {
            return criteriaBuilder.like(root.get(String.valueOf(column)), "%" + value + "%");
        }
        return null;
    }
}
