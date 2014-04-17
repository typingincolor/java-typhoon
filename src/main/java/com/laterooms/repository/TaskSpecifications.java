package com.laterooms.repository;

import com.laterooms.entity.Task;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Created by andrew on 16/04/2014.
 */
public class TaskSpecifications {
    public static Specification<Task> isUnprocessed() {
        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> taskRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(criteriaBuilder.isNull(taskRoot.get("statusCode")), criteriaBuilder.lessThan(taskRoot.<Date>get("at"), new Date()));
            }
        };
    }

    public static Specification<Task> readyToProcess() {
        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> taskRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(taskRoot.get("statusCode"));
            }
        };
    }
}
