package com.craftsoft.taskmanagementapi.domain;

import com.craftsoft.taskmanagementapi.domain.enums.Group;
import com.craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task extends TimeTrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "groups", nullable = false)
    @Enumerated(EnumType.STRING)
    private Group group;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "in_progress_at")
    private LocalDateTime inProgressAt;

    @Column(name = "in_test_at")
    private LocalDateTime inTestAt;

    @Column(name = "done_at")
    private LocalDateTime doneAt;

    @Column(name = "duration")
    private Long duration;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<SubTask> subTask;
}
