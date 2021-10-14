package craftsoft.taskmanagementapi.dao;

import craftsoft.taskmanagementapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
    void deleteById(long id);
}
