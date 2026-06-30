package com.itpm.service;

import com.itpm.model.Task;
import com.itpm.dto.TaskDTO;
import java.util.List;
import java.util.Optional;

/**
 * 任务业务接口
 */
public interface TaskService {
    Task createTask(TaskDTO dto);
    Optional<Task> findById(Long id);
    List<Task> findAll();
    List<Task> findByProject(Long projectId);
    List<Task> findByAssignee(Long userId);
    Task updateTask(Long id, TaskDTO dto);
    void deleteTask(Long id);
}
