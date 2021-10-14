package craftsoft.taskmanagementapi.service.impl;

import craftsoft.taskmanagementapi.dao.TaskRepository;
import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.dto.TaskDTO;
import craftsoft.taskmanagementapi.mapper.TaskMapper;
import craftsoft.taskmanagementapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        return taskMapper.toDTO(taskRepository.save(taskMapper.toDomain(taskDTO)));
    }

    @Override
    public Page<TaskDTO> getAllTasks(int page, int size) {
        return taskRepository.findAll(PageRequest.of(page, size)).map(task -> taskMapper.toDTO(task));
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO) {
        Task task = taskMapper.toDomain(taskDTO);
        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }
}
