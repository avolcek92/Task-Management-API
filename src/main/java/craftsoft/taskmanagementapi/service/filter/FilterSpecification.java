package craftsoft.taskmanagementapi.service.filter;

import craftsoft.taskmanagementapi.domain.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class FilterSpecification implements Specification<Task> {

    private String column;
    private String value;

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if (root.get(column).getJavaType() == String.class) {
                return criteriaBuilder.like(root.<String>get(column), "%" + value + "%");
            }
        return null;
    }
}
