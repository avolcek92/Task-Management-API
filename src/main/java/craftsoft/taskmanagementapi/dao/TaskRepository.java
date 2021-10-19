package craftsoft.taskmanagementapi.dao;

import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    void deleteById(int id);

    Optional<TaskResponseDTO> getTaskById(int id);
}
