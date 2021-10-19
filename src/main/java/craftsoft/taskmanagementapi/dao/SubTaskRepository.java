package craftsoft.taskmanagementapi.dao;

import craftsoft.taskmanagementapi.domain.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTaskRepository extends JpaRepository<SubTask, Integer> {
}
