package craftsoft.taskmanagementapi.service.filter;

import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.domain.enums.Group;
import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class GroupFilterSpecification implements Specification<Task> {
    private String column;
    private Group value;

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (value != null) {
            return criteriaBuilder.equal(root.get(column), value);
        }
        return null;
    }
}
