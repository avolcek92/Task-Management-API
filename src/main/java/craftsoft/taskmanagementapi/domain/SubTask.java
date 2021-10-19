package craftsoft.taskmanagementapi.domain;

import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "subtask")
public class SubTask extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
