package craftsoft.taskmanagementapi.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table( name = "subtask")
public class SubTask {

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
