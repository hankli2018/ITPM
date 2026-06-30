package com.itpm.service;

import com.itpm.dto.*;
import com.itpm.model.WbsNode;
import java.util.List;

/**
 * WBS节点服务接口
 */
public interface WbsNodeService {

    /**
     * 创建WBS节点
     */
    WbsNodeDTO createWbsNode(CreateWbsNodeRequest request);

    /**
     * 更新WBS节点
     */
    WbsNodeDTO updateWbsNode(Long id, UpdateWbsNodeRequest request);

    /**
     * 删除WBS节点
     */
    void deleteWbsNode(Long id);

    /**
     * 根据ID获取WBS节点
     */
    WbsNodeDTO getWbsNodeById(Long id);

    /**
     * 获取项目的WBS树结构
     */
    List<WbsNodeDTO> getProjectWbsTree(Long projectId);

    /**
     * 获取项目的WBS节点列表（扁平结构）
     */
    List<WbsNodeDTO> getProjectWbsNodes(Long projectId);

    /**
     * 获取节点的所有子节点
     */
    List<WbsNodeDTO> getChildNodes(Long parentId);

    /**
     * 从WBS节点生成任务
     */
    List<TaskDTO> generateTasksFromWbsNodes(GenerateTaskRequest request);

    /**
     * 统计项目的WBS信息
     */
    WbsStatistics getWbsStatistics(Long projectId);

    /**
     * 验证WBS编码是否可用
     */
    boolean validateWbsCode(Long projectId, String wbsCode, Long excludeId);

    /**
     * 重新计算节点的完成百分比
     */
    void recalculateCompletionPercentage(Long nodeId);

    /**
     * WBS统计数据
     */
    class WbsStatistics {
        private Long totalNodes;
        private Long phases;
        private Long deliverables;
        private Long workPackages;
        private Long tasks;
        private Double totalBudgetHours;
        private Double totalActualHours;
        private Double totalBudgetCost;
        private Double totalActualCost;
        private Integer overallProgress;

        // Getters and Setters
        public Long getTotalNodes() { return totalNodes; }
        public void setTotalNodes(Long totalNodes) { this.totalNodes = totalNodes; }
        public Long getPhases() { return phases; }
        public void setPhases(Long phases) { this.phases = phases; }
        public Long getDeliverables() { return deliverables; }
        public void setDeliverables(Long deliverables) { this.deliverables = deliverables; }
        public Long getWorkPackages() { return workPackages; }
        public void setWorkPackages(Long workPackages) { this.workPackages = workPackages; }
        public Long getTasks() { return tasks; }
        public void setTasks(Long tasks) { this.tasks = tasks; }
        public Double getTotalBudgetHours() { return totalBudgetHours; }
        public void setTotalBudgetHours(Double totalBudgetHours) { this.totalBudgetHours = totalBudgetHours; }
        public Double getTotalActualHours() { return totalActualHours; }
        public void setTotalActualHours(Double totalActualHours) { this.totalActualHours = totalActualHours; }
        public Double getTotalBudgetCost() { return totalBudgetCost; }
        public void setTotalBudgetCost(Double totalBudgetCost) { this.totalBudgetCost = totalBudgetCost; }
        public Double getTotalActualCost() { return totalActualCost; }
        public void setTotalActualCost(Double totalActualCost) { this.totalActualCost = totalActualCost; }
        public Integer getOverallProgress() { return overallProgress; }
        public void setOverallProgress(Integer overallProgress) { this.overallProgress = overallProgress; }
    }
}
