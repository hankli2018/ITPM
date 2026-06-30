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
}
