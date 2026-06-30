package com.itpm.dto;

import com.itpm.model.Project;
import java.time.LocalDateTime;

/**
 * 项目 DTO
 */
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

    // Constructors
    public ProjectDTO() {}

    public ProjectDTO(Long id, String name, String description, String projectCode, Long projectManagerId,
                     String status, LocalDateTime startDate, LocalDateTime endDate, Double budget,
                     Double actualCost, Integer progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectCode = projectCode;
        this.projectManagerId = projectManagerId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.actualCost = actualCost;
        this.progress = progress;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getProjectCode() { return projectCode; }
    public void setProjectCode(String projectCode) { this.projectCode = projectCode; }

    public Long getProjectManagerId() { return projectManagerId; }
    public void setProjectManagerId(Long projectManagerId) { this.projectManagerId = projectManagerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }

    public Double getActualCost() { return actualCost; }
    public void setActualCost(Double actualCost) { this.actualCost = actualCost; }

    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }

    public static ProjectDTO fromEntity(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setProjectCode(project.getProjectCode());
        dto.setProjectManagerId(project.getProjectManager() != null ? project.getProjectManager().getId() : null);
        dto.setStatus(project.getStatus().toString());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setBudget(project.getBudget());
        dto.setActualCost(project.getActualCost());
        dto.setProgress(project.getProgress());
        return dto;
    }
}
