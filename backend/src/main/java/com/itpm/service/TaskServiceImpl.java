package com.itpm.service;

import com.itpm.model.Task;
import com.itpm.model.Project;
import com.itpm.model.User;
import com.itpm.dto.TaskDTO;
import com.itpm.repository.TaskRepository;
import com.itpm.repository.ProjectRepository;
import com.itpm.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * 任务业务实现
 */
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(TaskDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("项目不存在"));

        User assignedTo = null;
        if (dto.getAssignedToId() != null) {
            assignedTo = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("用户不存在"));
        }

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setProject(project);
        task.setAssignedTo(assignedTo);
        task.setStatus(Task.TaskStatus.PENDING);
        task.setPriority(Task.TaskPriority.valueOf(dto.getPriority() != null ? dto.getPriority() : "MEDIUM"));
        task.setStartDate(dto.getStartDate());
        task.setEndDate(dto.getEndDate());
        task.setEstimatedHours(dto.getEstimatedHours());

        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public List<Task> findByAssignee(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }

    @Override
    public Task updateTask(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("任务不存在"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(Task.TaskStatus.valueOf(dto.getStatus()));
        task.setPriority(Task.TaskPriority.valueOf(dto.getPriority()));
        task.setCompletionPercentage(dto.getCompletionPercentage());
        task.setActualHours(dto.getActualHours());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
