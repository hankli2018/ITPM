package com.itpm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 项目实体
 */
@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "project_code", unique = true)
    private String projectCode;

    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private User projectManager;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "actual_cost")
    private Double actualCost = 0.0;

    @Column(name = "progress")
    private Integer progress = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getProjectCode() { return projectCode; }
    public User getProjectManager() { return projectManager; }
    public ProjectStatus getStatus() { return status; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Double getBudget() { return budget; }
    public Double getActualCost() { return actualCost; }
    public Integer getProgress() { return progress; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setProjectCode(String projectCode) { this.projectCode = projectCode; }
    public void setProjectManager(User projectManager) { this.projectManager = projectManager; }
    public void setStatus(ProjectStatus status) { this.status = status; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public void setBudget(Double budget) { this.budget = budget; }
    public void setActualCost(Double actualCost) { this.actualCost = actualCost; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public enum ProjectStatus {
        PLANNING, ACTIVE, SUSPENDED, COMPLETED, CANCELLED
    }
}
