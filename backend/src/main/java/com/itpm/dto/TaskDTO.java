package com.itpm.dto;

import com.itpm.model.Task;
import java.time.LocalDateTime;

/**
 * 任务 DTO
 */
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Long projectId;
    private Long assignedToId;
    private String status;
    private String priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer completionPercentage;
    private Double estimatedHours;
    private Double actualHours;

    // Constructors
    public TaskDTO() {}

    public TaskDTO(Long id, String title, String description, Long projectId, Long assignedToId,
                   String status, String priority, LocalDateTime startDate, LocalDateTime endDate,
                   Integer completionPercentage, Double estimatedHours, Double actualHours) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.projectId = projectId;
        this.assignedToId = assignedToId;
        this.status = status;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completionPercentage = completionPercentage;
        this.estimatedHours = estimatedHours;
        this.actualHours = actualHours;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Long assignedToId) { this.assignedToId = assignedToId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Integer getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(Integer completionPercentage) { this.completionPercentage = completionPercentage; }

    public Double getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Double estimatedHours) { this.estimatedHours = estimatedHours; }

    public Double getActualHours() { return actualHours; }
    public void setActualHours(Double actualHours) { this.actualHours = actualHours; }

    public static TaskDTO fromEntity(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setProjectId(task.getProject().getId());
        dto.setAssignedToId(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null);
        dto.setStatus(task.getStatus().toString());
        dto.setPriority(task.getPriority().toString());
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        dto.setCompletionPercentage(task.getCompletionPercentage());
        dto.setEstimatedHours(task.getEstimatedHours());
        dto.setActualHours(task.getActualHours());
        return dto;
    }
}
