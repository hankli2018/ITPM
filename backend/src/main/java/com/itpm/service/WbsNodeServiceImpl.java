package com.itpm.service;

import com.itpm.dto.*;
import com.itpm.model.Project;
import com.itpm.model.Task;
import com.itpm.model.User;
import com.itpm.model.WbsNode;
import com.itpm.repository.ProjectRepository;
import com.itpm.repository.TaskRepository;
import com.itpm.repository.UserRepository;
import com.itpm.repository.WbsNodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * WBS节点服务实现类
 */
@Service
@Transactional
public class WbsNodeServiceImpl implements WbsNodeService {

    private final WbsNodeRepository wbsNodeRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public WbsNodeServiceImpl(WbsNodeRepository wbsNodeRepository,
                               ProjectRepository projectRepository,
                               TaskRepository taskRepository,
                               UserRepository userRepository) {
        this.wbsNodeRepository = wbsNodeRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WbsNodeDTO createWbsNode(CreateWbsNodeRequest request) {
        // 验证项目存在
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("项目不存在"));

        WbsNode parent = null;
        if (request.getParentId() != null) {
            parent = wbsNodeRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("父节点不存在"));
        }

        // 生成WBS编码
        String wbsCode = request.getWbsCode();
        if (wbsCode == null || wbsCode.isEmpty()) {
            wbsCode = generateWbsCode(request.getProjectId(), parent);
        }

        // 计算层级
        int level = parent != null ? parent.getLevel() + 1 : 1;

        // 创建节点
        WbsNode node = WbsNode.builder()
                .project(project)
                .parent(parent)
                .name(request.getName())
                .wbsCode(wbsCode)
                .nodeType(WbsNode.WbsNodeType.valueOf(request.getNodeType()))
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .budgetHours(request.getBudgetHours() != null ? request.getBudgetHours() : 0.0)
                .actualHours(0.0)
                .budgetCost(request.getBudgetCost() != null ? request.getBudgetCost() : 0.0)
                .actualCost(0.0)
                .completionPercentage(0)
                .assignee(request.getAssigneeId() != null ?
                        userRepository.findById(request.getAssigneeId()).orElse(null) : null)
                .status(WbsNode.WbsNodeStatus.PLANNING)
                .description(request.getDescription())
                .level(level)
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .isMilestone(request.getIsMilestone() != null ? request.getIsMilestone() : false)
                .build();

        node = wbsNodeRepository.save(node);

        // 更新父节点的完成百分比
        if (parent != null) {
            recalculateCompletionPercentage(parent.getId());
        }

        return WbsNodeDTO.fromEntity(node);
    }

    @Override
    public WbsNodeDTO updateWbsNode(Long id, UpdateWbsNodeRequest request) {
        WbsNode node = wbsNodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WBS节点不存在"));

        if (request.getName() != null) {
            node.setName(request.getName());
        }

        if (request.getParentId() != null) {
            WbsNode newParent = wbsNodeRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("父节点不存在"));
            node.setParent(newParent);
            node.setLevel(newParent.getLevel() + 1);
        }

        if (request.getStartDate() != null) {
            node.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            node.setEndDate(request.getEndDate());
        }

        if (request.getBudgetHours() != null) {
            node.setBudgetHours(request.getBudgetHours());
        }

        if (request.getActualHours() != null) {
            node.setActualHours(request.getActualHours());
        }

        if (request.getBudgetCost() != null) {
            node.setBudgetCost(request.getBudgetCost());
        }

        if (request.getActualCost() != null) {
            node.setActualCost(request.getActualCost());
        }

        if (request.getCompletionPercentage() != null) {
            node.setCompletionPercentage(request.getCompletionPercentage());
        }

        if (request.getAssigneeId() != null) {
            node.setAssignee(userRepository.findById(request.getAssigneeId()).orElse(null));
        }

        if (request.getStatus() != null) {
            node.setStatus(WbsNode.WbsNodeStatus.valueOf(request.getStatus()));
        }

        if (request.getDescription() != null) {
            node.setDescription(request.getDescription());
        }

        if (request.getSortOrder() != null) {
            node.setSortOrder(request.getSortOrder());
        }

        if (request.getIsMilestone() != null) {
            node.setIsMilestone(request.getIsMilestone());
        }

        node = wbsNodeRepository.save(node);

        // 递归更新所有子节点的层级
        updateChildLevels(node);

        return WbsNodeDTO.fromEntity(node);
    }

    @Override
    public void deleteWbsNode(Long id) {
        WbsNode node = wbsNodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WBS节点不存在"));

        // 递归删除所有子节点
        deleteNodeAndChildren(node);

        // 更新父节点的完成百分比
        if (node.getParent() != null) {
            recalculateCompletionPercentage(node.getParent().getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public WbsNodeDTO getWbsNodeById(Long id) {
        WbsNode node = wbsNodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WBS节点不存在"));
        return WbsNodeDTO.fromEntity(node);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WbsNodeDTO> getProjectWbsTree(Long projectId) {
        List<WbsNode> allNodes = wbsNodeRepository.findByProjectIdOrderBySortOrderAsc(projectId);
        return buildTree(allNodes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WbsNodeDTO> getProjectWbsNodes(Long projectId) {
        List<WbsNode> nodes = wbsNodeRepository.findByProjectIdOrderBySortOrderAsc(projectId);
        return WbsNodeDTO.fromEntities(nodes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WbsNodeDTO> getChildNodes(Long parentId) {
        List<WbsNode> children = wbsNodeRepository.findByParentIdOrderBySortOrderAsc(parentId);
        return WbsNodeDTO.fromEntities(children);
    }

    @Override
    public List<TaskDTO> generateTasksFromWbsNodes(GenerateTaskRequest request) {
        List<Task> createdTasks = new ArrayList<>();

        for (Long wbsNodeId : request.getWbsNodeIds()) {
            WbsNode wbsNode = wbsNodeRepository.findById(wbsNodeId)
                    .orElseThrow(() -> new RuntimeException("WBS节点不存在: " + wbsNodeId));

            // 只从工作包和任务类型生成任务
            if (wbsNode.getNodeType() == WbsNode.WbsNodeType.WORK_PACKAGE ||
                wbsNode.getNodeType() == WbsNode.WbsNodeType.TASK) {

                Task task = createTaskFromWbsNode(wbsNode);
                createdTasks.add(task);
            }

            // 如果包含子节点
            if (Boolean.TRUE.equals(request.getIncludeChildren())) {
                List<WbsNode> descendants = wbsNodeRepository.findDescendants(wbsNodeId);
                for (WbsNode descendant : descendants) {
                    if (descendant.getId().equals(wbsNodeId)) continue;

                    if (descendant.getNodeType() == WbsNode.WbsNodeType.WORK_PACKAGE ||
                        descendant.getNodeType() == WbsNode.WbsNodeType.TASK) {
                        Task task = createTaskFromWbsNode(descendant);
                        createdTasks.add(task);
                    }
                }
            }
        }

        return createdTasks.stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WbsStatistics getWbsStatistics(Long projectId) {
        List<WbsNode> nodes = wbsNodeRepository.findByProjectIdOrderBySortOrderAsc(projectId);

        WbsStatistics stats = new WbsStatistics();
        stats.setTotalNodes((long) nodes.size());
        stats.setPhases(nodes.stream().filter(n -> n.getNodeType() == WbsNode.WbsNodeType.PHASE).count());
        stats.setDeliverables(nodes.stream().filter(n -> n.getNodeType() == WbsNode.WbsNodeType.DELIVERABLE).count());
        stats.setWorkPackages(nodes.stream().filter(n -> n.getNodeType() == WbsNode.WbsNodeType.WORK_PACKAGE).count());
        stats.setTasks(nodes.stream().filter(n -> n.getNodeType() == WbsNode.WbsNodeType.TASK).count());
        stats.setTotalBudgetHours(nodes.stream().mapToDouble(n -> n.getBudgetHours() != null ? n.getBudgetHours() : 0).sum());
        stats.setTotalActualHours(nodes.stream().mapToDouble(n -> n.getActualHours() != null ? n.getActualHours() : 0).sum());
        stats.setTotalBudgetCost(nodes.stream().mapToDouble(n -> n.getBudgetCost() != null ? n.getBudgetCost() : 0).sum());
        stats.setTotalActualCost(nodes.stream().mapToDouble(n -> n.getActualCost() != null ? n.getActualCost() : 0).sum());

        // 计算整体进度
        if (!nodes.isEmpty()) {
            int totalProgress = nodes.stream()
                    .mapToInt(n -> n.getCompletionPercentage() != null ? n.getCompletionPercentage() : 0)
                    .sum();
            stats.setOverallProgress(totalProgress / nodes.size());
        } else {
            stats.setOverallProgress(0);
        }

        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateWbsCode(Long projectId, String wbsCode, Long excludeId) {
        WbsNode existing = wbsNodeRepository.findByProjectIdAndWbsCode(projectId, wbsCode);
        if (existing == null) {
            return true;
        }
        return excludeId != null && existing.getId().equals(excludeId);
    }

    @Override
    public void recalculateCompletionPercentage(Long nodeId) {
        WbsNode node = wbsNodeRepository.findById(nodeId).orElse(null);
        if (node == null) return;

        List<WbsNode> children = wbsNodeRepository.findByParentIdOrderBySortOrderAsc(nodeId);
        if (children.isEmpty()) {
            return;
        }

        // 计算子节点的平均完成百分比
        int totalProgress = children.stream()
                .mapToInt(c -> c.getCompletionPercentage() != null ? c.getCompletionPercentage() : 0)
                .sum();
        int avgProgress = totalProgress / children.size();

        node.setCompletionPercentage(avgProgress);
        wbsNodeRepository.save(node);

        // 递归更新父节点
        if (node.getParent() != null) {
            recalculateCompletionPercentage(node.getParent().getId());
        }
    }

    /**
     * 生成WBS编码
     */
    private String generateWbsCode(Long projectId, WbsNode parent) {
        if (parent == null) {
            // 顶级节点
            List<WbsNode> rootNodes = wbsNodeRepository.findByProjectIdAndParentIsNullOrderBySortOrderAsc(projectId);
            return String.valueOf(rootNodes.size() + 1);
        } else {
            // 子节点
            List<WbsNode> siblings = wbsNodeRepository.findByParentIdOrderBySortOrderAsc(parent.getId());
            return parent.getWbsCode() + "." + (siblings.size() + 1);
        }
    }

    /**
     * 构建WBS树
     */
    private List<WbsNodeDTO> buildTree(List<WbsNode> nodes) {
        Map<Long, WbsNodeDTO> nodeMap = new HashMap<>();
        List<WbsNodeDTO> rootNodes = new ArrayList<>();

        // 首先转换所有节点
        for (WbsNode node : nodes) {
            nodeMap.put(node.getId(), WbsNodeDTO.fromEntity(node));
        }

        // 然后构建父子关系
        for (WbsNode node : nodes) {
            WbsNodeDTO dto = nodeMap.get(node.getId());
            if (node.getParent() == null) {
                rootNodes.add(dto);
            } else {
                WbsNodeDTO parentDto = nodeMap.get(node.getParent().getId());
                if (parentDto != null) {
                    if (parentDto.getChildren() == null) {
                        parentDto.setChildren(new ArrayList<>());
                    }
                    parentDto.getChildren().add(dto);
                }
            }
        }

        return rootNodes;
    }

    /**
     * 递归删除节点及其子节点
     */
    private void deleteNodeAndChildren(WbsNode node) {
        List<WbsNode> children = wbsNodeRepository.findByParentIdOrderBySortOrderAsc(node.getId());
        for (WbsNode child : children) {
            deleteNodeAndChildren(child);
        }
        wbsNodeRepository.delete(node);
    }

    /**
     * 更新子节点的层级
     */
    private void updateChildLevels(WbsNode parent) {
        List<WbsNode> children = wbsNodeRepository.findByParentIdOrderBySortOrderAsc(parent.getId());
        for (WbsNode child : children) {
            child.setLevel(parent.getLevel() + 1);
            wbsNodeRepository.save(child);
            updateChildLevels(child);
        }
    }

    /**
     * 从WBS节点创建任务
     */
    private Task createTaskFromWbsNode(WbsNode wbsNode) {
        Task task = Task.builder()
                .title(wbsNode.getName())
                .description(wbsNode.getDescription())
                .project(wbsNode.getProject())
                .assignedTo(wbsNode.getAssignee())
                .status(Task.TaskStatus.PENDING)
                .priority(Task.TaskPriority.MEDIUM)
                .startDate(wbsNode.getStartDate())
                .endDate(wbsNode.getEndDate())
                .estimatedHours(wbsNode.getBudgetHours())
                .actualHours(wbsNode.getActualHours())
                .completionPercentage(wbsNode.getCompletionPercentage())
                .build();

        return taskRepository.save(task);
    }
}
