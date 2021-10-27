package com.craftsoft.taskmanagementapi.dao;

import com.craftsoft.taskmanagementapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
}
