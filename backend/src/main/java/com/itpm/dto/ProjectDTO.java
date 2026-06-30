package com.itpm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.itpm.model.Project;
import java.time.LocalDateTime;

/**
 * 项目 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String projectCode;
    private Long projectManagerId;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double budget;
    private Double actualCost;
    private Integer progress;

    public static ProjectDTO fromEntity(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .projectCode(project.getProjectCode())
                .projectManagerId(project.getProjectManager() != null ? project.getProjectManager().getId() : null)
                .status(project.getStatus().toString())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .budget(project.getBudget())
                .actualCost(project.getActualCost())
                .progress(project.getProgress())
                .build();
    }
}
