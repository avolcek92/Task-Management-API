package craftsoft.taskmanagementapi.mapper;

import craftsoft.taskmanagementapi.domain.SubTask;
import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.dto.SubTaskDTO;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component("taskMapper")
public class TaskMapper {

    @Autowired
    SubTaskMapper subTaskMapper;

    public TaskResponseDTO toDTO(Task taskDomain) {
        return TaskResponseDTO.builder()
                .id(taskDomain.getId())
                .name(taskDomain.getName())
                .description(taskDomain.getDescription())
                .group(taskDomain.getGroup())
                .status(taskDomain.getStatus())
                .assignee(taskDomain.getAssignee())
                .duration(taskDomain.getDuration())
                .subTask(taskDomain.getSubTask() == null ? new ArrayList<SubTaskDTO>() : taskDomain.getSubTask().stream().map(subTask -> subTaskMapper.toDTO(subTask)).collect(Collectors.toList()))
                .build();
    }

    public Task toDomain (TaskResponseDTO taskResponseDTO) {
        return Task.builder()
                .name(taskResponseDTO.getName())
                .description(taskResponseDTO.getDescription())
                .group(taskResponseDTO.getGroup())
                .status(taskResponseDTO.getStatus())
                .assignee(taskResponseDTO.getAssignee())
                .duration(taskResponseDTO.getDuration())
                .subTask(taskResponseDTO.getSubTask() == null ? new ArrayList<SubTask>() : taskResponseDTO.getSubTask().stream().map(subTaskDTO -> subTaskMapper.toDomain(subTaskDTO)).collect(Collectors.toList()))
                .build();
    }
}
