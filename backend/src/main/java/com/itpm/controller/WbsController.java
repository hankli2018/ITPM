package com.itpm.controller;

import com.itpm.dto.*;
import com.itpm.service.WbsNodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WBS计划控制器
 */
@RestController
@RequestMapping("/wbs")
@CrossOrigin(origins = "*")
public class WbsController {

    private final WbsNodeService wbsNodeService;

    public WbsController(WbsNodeService wbsNodeService) {
        this.wbsNodeService = wbsNodeService;
    }

    /**
     * 创建WBS节点
     */
    @PostMapping
    public ResponseEntity<ApiResponse<WbsNodeDTO>> createWbsNode(@Valid @RequestBody CreateWbsNodeRequest request) {
        try {
            WbsNodeDTO node = wbsNodeService.createWbsNode(request);
            return ResponseEntity.ok(ApiResponse.success("WBS节点创建成功", node));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 更新WBS节点
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WbsNodeDTO>> updateWbsNode(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWbsNodeRequest request) {
        try {
            WbsNodeDTO node = wbsNodeService.updateWbsNode(id, request);
            return ResponseEntity.ok(ApiResponse.success("WBS节点更新成功", node));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 删除WBS节点
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWbsNode(@PathVariable Long id) {
        try {
            wbsNodeService.deleteWbsNode(id);
            return ResponseEntity.ok(ApiResponse.success("WBS节点删除成功", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取WBS节点详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WbsNodeDTO>> getWbsNodeById(@PathVariable Long id) {
        try {
            WbsNodeDTO node = wbsNodeService.getWbsNodeById(id);
            return ResponseEntity.ok(ApiResponse.success("获取成功", node));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取项目的WBS树结构
     */
    @GetMapping("/project/{projectId}/tree")
    public ResponseEntity<ApiResponse<List<WbsNodeDTO>>> getProjectWbsTree(@PathVariable Long projectId) {
        try {
            List<WbsNodeDTO> tree = wbsNodeService.getProjectWbsTree(projectId);
            return ResponseEntity.ok(ApiResponse.success("获取成功", tree));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取项目的WBS节点列表（扁平结构）
     */
    @GetMapping("/project/{projectId}/nodes")
    public ResponseEntity<ApiResponse<List<WbsNodeDTO>>> getProjectWbsNodes(@PathVariable Long projectId) {
        try {
            List<WbsNodeDTO> nodes = wbsNodeService.getProjectWbsNodes(projectId);
            return ResponseEntity.ok(ApiResponse.success("获取成功", nodes));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取子节点列表
     */
    @GetMapping("/{id}/children")
    public ResponseEntity<ApiResponse<List<WbsNodeDTO>>> getChildNodes(@PathVariable Long id) {
        try {
            List<WbsNodeDTO> children = wbsNodeService.getChildNodes(id);
            return ResponseEntity.ok(ApiResponse.success("获取成功", children));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 从WBS节点生成项目任务
     */
    @PostMapping("/generate-tasks")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> generateTasks(@Valid @RequestBody GenerateTaskRequest request) {
        try {
            List<TaskDTO> tasks = wbsNodeService.generateTasksFromWbsNodes(request);
            return ResponseEntity.ok(ApiResponse.success("任务生成成功，共生成" + tasks.size() + "个任务", tasks));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取WBS统计数据
     */
    @GetMapping("/project/{projectId}/statistics")
    public ResponseEntity<ApiResponse<WbsNodeService.WbsStatistics>> getWbsStatistics(@PathVariable Long projectId) {
        try {
            WbsNodeService.WbsStatistics stats = wbsNodeService.getWbsStatistics(projectId);
            return ResponseEntity.ok(ApiResponse.success("获取成功", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 验证WBS编码是否可用
     */
    @GetMapping("/validate-code")
    public ResponseEntity<ApiResponse<Boolean>> validateWbsCode(
            @RequestParam Long projectId,
            @RequestParam String wbsCode,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean valid = wbsNodeService.validateWbsCode(projectId, wbsCode, excludeId);
            return ResponseEntity.ok(ApiResponse.success(valid ? "编码可用" : "编码已存在", valid));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 重新计算节点完成百分比
     */
    @PostMapping("/{id}/recalculate")
    public ResponseEntity<ApiResponse<Void>> recalculateCompletionPercentage(@PathVariable Long id) {
        try {
            wbsNodeService.recalculateCompletionPercentage(id);
            return ResponseEntity.ok(ApiResponse.success("重新计算成功", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
