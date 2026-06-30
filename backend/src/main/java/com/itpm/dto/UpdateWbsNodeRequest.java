package com.itpm.dto;

import java.time.LocalDateTime;

/**
 * 更新WBS节点请求DTO
 */
public class UpdateWbsNodeRequest {

    private String name;
    private Long parentId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double budgetHours;
    private Double actualHours;
    private Double budgetCost;
    private Double actualCost;
    private Integer completionPercentage;
    private Long assigneeId;
    private String status;
    private String description;
    private Integer sortOrder;
    private Boolean isMilestone;

    public UpdateWbsNodeRequest() {}

    public UpdateWbsNodeRequest(String name, Long parentId, LocalDateTime startDate, LocalDateTime endDate,
                               Double budgetHours, Double actualHours, Double budgetCost, Double actualCost,
                               Integer completionPercentage, Long assigneeId, String status, String description,
                               Integer sortOrder, Boolean isMilestone) {
        this.name = name;
        this.parentId = parentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetHours = budgetHours;
        this.actualHours = actualHours;
        this.budgetCost = budgetCost;
        this.actualCost = actualCost;
        this.completionPercentage = completionPercentage;
        this.assigneeId = assigneeId;
        this.status = status;
        this.description = description;
        this.sortOrder = sortOrder;
        this.isMilestone = isMilestone;
    }

    // Getters
    public String getName() { return name; }
    public Long getParentId() { return parentId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Double getBudgetHours() { return budgetHours; }
    public Double getActualHours() { return actualHours; }
    public Double getBudgetCost() { return budgetCost; }
    public Double getActualCost() { return actualCost; }
    public Integer getCompletionPercentage() { return completionPercentage; }
    public Long getAssigneeId() { return assigneeId; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
    public Integer getSortOrder() { return sortOrder; }
    public Boolean getIsMilestone() { return isMilestone; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public void setBudgetHours(Double budgetHours) { this.budgetHours = budgetHours; }
    public void setActualHours(Double actualHours) { this.actualHours = actualHours; }
    public void setBudgetCost(Double budgetCost) { this.budgetCost = budgetCost; }
    public void setActualCost(Double actualCost) { this.actualCost = actualCost; }
    public void setCompletionPercentage(Integer completionPercentage) { this.completionPercentage = completionPercentage; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
    public void setStatus(String status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public void setIsMilestone(Boolean isMilestone) { this.isMilestone = isMilestone; }

    // Builder pattern
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String name;
        private Long parentId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Double budgetHours;
        private Double actualHours;
        private Double budgetCost;
        private Double actualCost;
        private Integer completionPercentage;
        private Long assigneeId;
        private String status;
        private String description;
        private Integer sortOrder;
        private Boolean isMilestone;

        public Builder name(String name) { this.name = name; return this; }
        public Builder parentId(Long parentId) { this.parentId = parentId; return this; }
        public Builder startDate(LocalDateTime startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDateTime endDate) { this.endDate = endDate; return this; }
        public Builder budgetHours(Double budgetHours) { this.budgetHours = budgetHours; return this; }
        public Builder actualHours(Double actualHours) { this.actualHours = actualHours; return this; }
        public Builder budgetCost(Double budgetCost) { this.budgetCost = budgetCost; return this; }
        public Builder actualCost(Double actualCost) { this.actualCost = actualCost; return this; }
        public Builder completionPercentage(Integer completionPercentage) { this.completionPercentage = completionPercentage; return this; }
        public Builder assigneeId(Long assigneeId) { this.assigneeId = assigneeId; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder sortOrder(Integer sortOrder) { this.sortOrder = sortOrder; return this; }
        public Builder isMilestone(Boolean isMilestone) { this.isMilestone = isMilestone; return this; }

        public UpdateWbsNodeRequest build() {
            return new UpdateWbsNodeRequest(name, parentId, startDate, endDate, budgetHours, actualHours,
                    budgetCost, actualCost, completionPercentage, assigneeId, status, description, sortOrder, isMilestone);
        }
    }
}
