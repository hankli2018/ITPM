package com.itpm.repository;

import com.itpm.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 任务数据访问接口
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssignedToId(Long userId);
    List<Task> findByProjectIdAndStatus(Long projectId, Task.TaskStatus status);
    List<Task> findByStatus(Task.TaskStatus status);
}
