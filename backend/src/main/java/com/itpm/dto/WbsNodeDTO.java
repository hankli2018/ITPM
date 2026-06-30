package com.itpm.dto;

import com.itpm.model.WbsNode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WBS节点DTO
 */
public class WbsNodeDTO {

    private Long id;
    private Long projectId;
    private String projectName;
    private Long parentId;
    private String parentName;
    private String name;
    private String wbsCode;
    private String nodeType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double budgetHours;
    private Double actualHours;
    private Double budgetCost;
    private Double actualCost;
    private Integer completionPercentage;
    private Long assigneeId;
    private String assigneeName;
    private String status;
    private String description;
    private Integer level;
    private Integer sortOrder;
    private Boolean isMilestone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<WbsNodeDTO> children;

    public WbsNodeDTO() {}

    // Getters
    public Long getId() { return id; }
    public Long getProjectId() { return projectId; }
    public String getProjectName() { return projectName; }
    public Long getParentId() { return parentId; }
    public String getParentName() { return parentName; }
    public String getName() { return name; }
    public String getWbsCode() { return wbsCode; }
    public String getNodeType() { return nodeType; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Double getBudgetHours() { return budgetHours; }
    public Double getActualHours() { return actualHours; }
    public Double getBudgetCost() { return budgetCost; }
    public Double getActualCost() { return actualCost; }
    public Integer getCompletionPercentage() { return completionPercentage; }
    public Long getAssigneeId() { return assigneeId; }
    public String getAssigneeName() { return assigneeName; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
    public Integer getLevel() { return level; }
    public Integer getSortOrder() { return sortOrder; }
    public Boolean getIsMilestone() { return isMilestone; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<WbsNodeDTO> getChildren() { return children; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public void setParentName(String parentName) { this.parentName = parentName; }
    public void setName(String name) { this.name = name; }
    public void setWbsCode(String wbsCode) { this.wbsCode = wbsCode; }
    public void setNodeType(String nodeType) { this.nodeType = nodeType; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public void setBudgetHours(Double budgetHours) { this.budgetHours = budgetHours; }
    public void setActualHours(Double actualHours) { this.actualHours = actualHours; }
    public void setBudgetCost(Double budgetCost) { this.budgetCost = budgetCost; }
    public void setActualCost(Double actualCost) { this.actualCost = actualCost; }
    public void setCompletionPercentage(Integer completionPercentage) { this.completionPercentage = completionPercentage; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
    public void setAssigneeName(String assigneeName) { this.assigneeName = assigneeName; }
    public void setStatus(String status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }
    public void setLevel(Integer level) { this.level = level; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public void setIsMilestone(Boolean isMilestone) { this.isMilestone = isMilestone; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setChildren(List<WbsNodeDTO> children) { this.children = children; }

    /**
     * 从实体转换为DTO
     */
    public static WbsNodeDTO fromEntity(WbsNode node) {
        return builder()
                .id(node.getId())
                .projectId(node.getProject() != null ? node.getProject().getId() : null)
                .projectName(node.getProject() != null ? node.getProject().getName() : null)
                .parentId(node.getParent() != null ? node.getParent().getId() : null)
                .parentName(node.getParent() != null ? node.getParent().getName() : null)
                .name(node.getName())
                .wbsCode(node.getWbsCode())
                .nodeType(node.getNodeType() != null ? node.getNodeType().name() : null)
                .startDate(node.getStartDate())
                .endDate(node.getEndDate())
                .budgetHours(node.getBudgetHours())
                .actualHours(node.getActualHours())
                .budgetCost(node.getBudgetCost())
                .actualCost(node.getActualCost())
                .completionPercentage(node.getCompletionPercentage())
                .assigneeId(node.getAssignee() != null ? node.getAssignee().getId() : null)
                .assigneeName(node.getAssignee() != null ? node.getAssignee().getRealName() : null)
                .status(node.getStatus() != null ? node.getStatus().name() : null)
                .description(node.getDescription())
                .level(node.getLevel())
                .sortOrder(node.getSortOrder())
                .isMilestone(node.getIsMilestone())
                .createdAt(node.getCreatedAt())
                .updatedAt(node.getUpdatedAt())
                .build();
    }

    /**
     * 批量转换
     */
    public static List<WbsNodeDTO> fromEntities(List<WbsNode> nodes) {
        return nodes.stream()
                .map(WbsNodeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long projectId;
        private String projectName;
        private Long parentId;
        private String parentName;
        private String name;
        private String wbsCode;
        private String nodeType;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Double budgetHours;
        private Double actualHours;
        private Double budgetCost;
        private Double actualCost;
        private Integer completionPercentage;
        private Long assigneeId;
        private String assigneeName;
        private String status;
        private String description;
        private Integer level;
        private Integer sortOrder;
        private Boolean isMilestone;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<WbsNodeDTO> children;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder projectId(Long projectId) { this.projectId = projectId; return this; }
        public Builder projectName(String projectName) { this.projectName = projectName; return this; }
        public Builder parentId(Long parentId) { this.parentId = parentId; return this; }
        public Builder parentName(String parentName) { this.parentName = parentName; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder wbsCode(String wbsCode) { this.wbsCode = wbsCode; return this; }
        public Builder nodeType(String nodeType) { this.nodeType = nodeType; return this; }
        public Builder startDate(LocalDateTime startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDateTime endDate) { this.endDate = endDate; return this; }
        public Builder budgetHours(Double budgetHours) { this.budgetHours = budgetHours; return this; }
        public Builder actualHours(Double actualHours) { this.actualHours = actualHours; return this; }
        public Builder budgetCost(Double budgetCost) { this.budgetCost = budgetCost; return this; }
        public Builder actualCost(Double actualCost) { this.actualCost = actualCost; return this; }
        public Builder completionPercentage(Integer completionPercentage) { this.completionPercentage = completionPercentage; return this; }
        public Builder assigneeId(Long assigneeId) { this.assigneeId = assigneeId; return this; }
        public Builder assigneeName(String assigneeName) { this.assigneeName = assigneeName; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder level(Integer level) { this.level = level; return this; }
        public Builder sortOrder(Integer sortOrder) { this.sortOrder = sortOrder; return this; }
        public Builder isMilestone(Boolean isMilestone) { this.isMilestone = isMilestone; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder children(List<WbsNodeDTO> children) { this.children = children; return this; }

        public WbsNodeDTO build() {
            WbsNodeDTO dto = new WbsNodeDTO();
            dto.setId(id);
            dto.setProjectId(projectId);
            dto.setProjectName(projectName);
            dto.setParentId(parentId);
            dto.setParentName(parentName);
            dto.setName(name);
            dto.setWbsCode(wbsCode);
            dto.setNodeType(nodeType);
            dto.setStartDate(startDate);
            dto.setEndDate(endDate);
            dto.setBudgetHours(budgetHours);
            dto.setActualHours(actualHours);
            dto.setBudgetCost(budgetCost);
            dto.setActualCost(actualCost);
            dto.setCompletionPercentage(completionPercentage);
            dto.setAssigneeId(assigneeId);
            dto.setAssigneeName(assigneeName);
            dto.setStatus(status);
            dto.setDescription(description);
            dto.setLevel(level);
            dto.setSortOrder(sortOrder);
            dto.setIsMilestone(isMilestone);
            dto.setCreatedAt(createdAt);
            dto.setUpdatedAt(updatedAt);
            dto.setChildren(children);
            return dto;
        }
    }
}
