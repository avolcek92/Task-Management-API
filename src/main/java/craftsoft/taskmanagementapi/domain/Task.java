package craftsoft.taskmanagementapi.domain;

import craftsoft.taskmanagementapi.domain.enums.Group;
import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table( name = "task")
public class Task {

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "group", nullable = false)
    private Group group;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "duration", nullable = false)
    private long duration;

    @OneToMany(mappedBy = "task")
    private List<SubTask> subTask;
}
