package com.itpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 创建WBS节点请求DTO
 */
public class CreateWbsNodeRequest {

    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    private Long parentId;

    @NotBlank(message = "节点名称不能为空")
    private String name;

    private String wbsCode;

    @NotNull(message = "节点类型不能为空")
    private String nodeType;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double budgetHours;

    private Double budgetCost;

    private Long assigneeId;

    private String description;

    private Integer sortOrder;

    private Boolean isMilestone;

    public CreateWbsNodeRequest() {}

    public CreateWbsNodeRequest(Long projectId, Long parentId, String name, String wbsCode, String nodeType,
                                LocalDateTime startDate, LocalDateTime endDate, Double budgetHours, Double budgetCost,
                                Long assigneeId, String description, Integer sortOrder, Boolean isMilestone) {
        this.projectId = projectId;
        this.parentId = parentId;
        this.name = name;
        this.wbsCode = wbsCode;
        this.nodeType = nodeType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetHours = budgetHours;
        this.budgetCost = budgetCost;
        this.assigneeId = assigneeId;
        this.description = description;
        this.sortOrder = sortOrder;
        this.isMilestone = isMilestone;
    }

    // Getters
    public Long getProjectId() { return projectId; }
    public Long getParentId() { return parentId; }
    public String getName() { return name; }
    public String getWbsCode() { return wbsCode; }
    public String getNodeType() { return nodeType; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Double getBudgetHours() { return budgetHours; }
    public Double getBudgetCost() { return budgetCost; }
    public Long getAssigneeId() { return assigneeId; }
    public String getDescription() { return description; }
    public Integer getSortOrder() { return sortOrder; }
    public Boolean getIsMilestone() { return isMilestone; }

    // Setters
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public void setName(String name) { this.name = name; }
    public void setWbsCode(String wbsCode) { this.wbsCode = wbsCode; }
    public void setNodeType(String nodeType) { this.nodeType = nodeType; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public void setBudgetHours(Double budgetHours) { this.budgetHours = budgetHours; }
    public void setBudgetCost(Double budgetCost) { this.budgetCost = budgetCost; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
    public void setDescription(String description) { this.description = description; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public void setIsMilestone(Boolean isMilestone) { this.isMilestone = isMilestone; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long projectId;
        private Long parentId;
        private String name;
        private String wbsCode;
        private String nodeType;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Double budgetHours;
        private Double budgetCost;
        private Long assigneeId;
        private String description;
        private Integer sortOrder;
        private Boolean isMilestone;

        public Builder projectId(Long projectId) { this.projectId = projectId; return this; }
        public Builder parentId(Long parentId) { this.parentId = parentId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder wbsCode(String wbsCode) { this.wbsCode = wbsCode; return this; }
        public Builder nodeType(String nodeType) { this.nodeType = nodeType; return this; }
        public Builder startDate(LocalDateTime startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDateTime endDate) { this.endDate = endDate; return this; }
        public Builder budgetHours(Double budgetHours) { this.budgetHours = budgetHours; return this; }
        public Builder budgetCost(Double budgetCost) { this.budgetCost = budgetCost; return this; }
        public Builder assigneeId(Long assigneeId) { this.assigneeId = assigneeId; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder sortOrder(Integer sortOrder) { this.sortOrder = sortOrder; return this; }
        public Builder isMilestone(Boolean isMilestone) { this.isMilestone = isMilestone; return this; }

        public CreateWbsNodeRequest build() {
            return new CreateWbsNodeRequest(projectId, parentId, name, wbsCode, nodeType, startDate, endDate,
                    budgetHours, budgetCost, assigneeId, description, sortOrder, isMilestone);
        }
    }
}
