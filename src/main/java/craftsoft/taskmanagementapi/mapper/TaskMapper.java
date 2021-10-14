package craftsoft.taskmanagementapi.mapper;

import craftsoft.taskmanagementapi.domain.SubTask;
import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.dto.SubTaskDTO;
import craftsoft.taskmanagementapi.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("taskMapper")
public class TaskMapper {

    @Autowired
    SubTaskMapper subTaskMapper;

    public TaskDTO toDTO(Task taskDomain) {
        return TaskDTO.builder()
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

    public Task toDomain (TaskDTO taskDTO) {
        return Task.builder()
                .id(taskDTO.getId())
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .group(taskDTO.getGroup())
                .status(taskDTO.getStatus())
                .assignee(taskDTO.getAssignee())
                .duration(taskDTO.getDuration())
                .subTask(taskDTO.getSubTask() == null ? new ArrayList<SubTask>() : taskDTO.getSubTask().stream().map(subTaskDTO -> subTaskMapper.toDomain(subTaskDTO)).collect(Collectors.toList()))
                .build();
    }
}
