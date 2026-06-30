package com.itpm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.itpm.model.Task;
import java.time.LocalDateTime;

/**
 * 任务 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public static TaskDTO fromEntity(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .projectId(task.getProject().getId())
                .assignedToId(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null)
                .status(task.getStatus().toString())
                .priority(task.getPriority().toString())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .completionPercentage(task.getCompletionPercentage())
                .estimatedHours(task.getEstimatedHours())
                .actualHours(task.getActualHours())
                .build();
    }
}
