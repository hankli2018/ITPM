package com.itpm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * WBS节点实体 - 工作分解结构
 */
@Entity
@Table(name = "wbs_nodes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WbsNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属项目
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * 父节点（顶层节点为null）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private WbsNode parent;

    /**
     * 节点名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * WBS编码（如：1, 1.1, 1.1.1）
     */
    @Column(name = "wbs_code")
    private String wbsCode;

    /**
     * 节点类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "node_type", nullable = false)
    private WbsNodeType nodeType;

    /**
     * 开始日期
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * 预算工时（小时）
     */
    @Column(name = "budget_hours")
    private Double budgetHours = 0.0;

    /**
     * 实际工时（小时）
     */
    @Column(name = "actual_hours")
    private Double actualHours = 0.0;

    /**
     * 预算成本
     */
    @Column(name = "budget_cost")
    private Double budgetCost = 0.0;

    /**
     * 实际成本
     */
    @Column(name = "actual_cost")
    private Double actualCost = 0.0;

    /**
     * 完成百分比（0-100）
     */
    @Column(name = "completion_percentage")
    private Integer completionPercentage = 0;

    /**
     * 负责人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WbsNodeStatus status;

    /**
     * 描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 层级深度（从1开始）
     */
    @Column(nullable = false)
    private Integer level = 1;

    /**
     * 排序序号
     */
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    /**
     * 是否里程碑
     */
    @Column(name = "is_milestone")
    private Boolean isMilestone = false;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
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
    public Project getProject() { return project; }
    public WbsNode getParent() { return parent; }
    public String getName() { return name; }
    public String getWbsCode() { return wbsCode; }
    public WbsNodeType getNodeType() { return nodeType; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Double getBudgetHours() { return budgetHours; }
    public Double getActualHours() { return actualHours; }
    public Double getBudgetCost() { return budgetCost; }
    public Double getActualCost() { return actualCost; }
    public Integer getCompletionPercentage() { return completionPercentage; }
    public User getAssignee() { return assignee; }
    public WbsNodeStatus getStatus() { return status; }
    public String getDescription() { return description; }
    public Integer getLevel() { return level; }
    public Integer getSortOrder() { return sortOrder; }
    public Boolean getIsMilestone() { return isMilestone; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setProject(Project project) { this.project = project; }
    public void setParent(WbsNode parent) { this.parent = parent; }
    public void setName(String name) { this.name = name; }
    public void setWbsCode(String wbsCode) { this.wbsCode = wbsCode; }
    public void setNodeType(WbsNodeType nodeType) { this.nodeType = nodeType; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public void setBudgetHours(Double budgetHours) { this.budgetHours = budgetHours; }
    public void setActualHours(Double actualHours) { this.actualHours = actualHours; }
    public void setBudgetCost(Double budgetCost) { this.budgetCost = budgetCost; }
    public void setActualCost(Double actualCost) { this.actualCost = actualCost; }
    public void setCompletionPercentage(Integer completionPercentage) { this.completionPercentage = completionPercentage; }
    public void setAssignee(User assignee) { this.assignee = assignee; }
    public void setStatus(WbsNodeStatus status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }
    public void setLevel(Integer level) { this.level = level; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public void setIsMilestone(Boolean isMilestone) { this.isMilestone = isMilestone; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    /**
     * WBS节点类型枚举
     */
    public enum WbsNodeType {
        PROJECT,      // 项目
        PHASE,        // 阶段
        DELIVERABLE,  // 可交付成果
        WORK_PACKAGE, // 工作包
        TASK          // 任务
    }

    /**
     * WBS节点状态枚举
     */
    public enum WbsNodeStatus {
        PLANNING,     // 计划中
        IN_PROGRESS,  // 进行中
        COMPLETED,    // 已完成
        ON_HOLD,      // 暂停
        CANCELLED     // 已取消
    }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Project project;
        private WbsNode parent;
        private String name;
        private String wbsCode;
        private WbsNodeType nodeType;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Double budgetHours;
        private Double actualHours;
        private Double budgetCost;
        private Double actualCost;
        private Integer completionPercentage;
        private User assignee;
        private WbsNodeStatus status;
        private String description;
        private Integer level;
        private Integer sortOrder;
        private Boolean isMilestone;

        public Builder project(Project project) { this.project = project; return this; }
        public Builder parent(WbsNode parent) { this.parent = parent; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder wbsCode(String wbsCode) { this.wbsCode = wbsCode; return this; }
        public Builder nodeType(WbsNodeType nodeType) { this.nodeType = nodeType; return this; }
        public Builder startDate(LocalDateTime startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDateTime endDate) { this.endDate = endDate; return this; }
        public Builder budgetHours(Double budgetHours) { this.budgetHours = budgetHours; return this; }
        public Builder actualHours(Double actualHours) { this.actualHours = actualHours; return this; }
        public Builder budgetCost(Double budgetCost) { this.budgetCost = budgetCost; return this; }
        public Builder actualCost(Double actualCost) { this.actualCost = actualCost; return this; }
        public Builder completionPercentage(Integer completionPercentage) { this.completionPercentage = completionPercentage; return this; }
        public Builder assignee(User assignee) { this.assignee = assignee; return this; }
        public Builder status(WbsNodeStatus status) { this.status = status; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder level(Integer level) { this.level = level; return this; }
        public Builder sortOrder(Integer sortOrder) { this.sortOrder = sortOrder; return this; }
        public Builder isMilestone(Boolean isMilestone) { this.isMilestone = isMilestone; return this; }

        public WbsNode build() {
            WbsNode node = new WbsNode();
            node.setProject(project);
            node.setParent(parent);
            node.setName(name);
            node.setWbsCode(wbsCode);
            node.setNodeType(nodeType);
            node.setStartDate(startDate);
            node.setEndDate(endDate);
            node.setBudgetHours(budgetHours);
            node.setActualHours(actualHours);
            node.setBudgetCost(budgetCost);
            node.setActualCost(actualCost);
            node.setCompletionPercentage(completionPercentage);
            node.setAssignee(assignee);
            node.setStatus(status);
            node.setDescription(description);
            node.setLevel(level);
            node.setSortOrder(sortOrder);
            node.setIsMilestone(isMilestone);
            return node;
        }
    }
}
