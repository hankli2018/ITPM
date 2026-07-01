package com.itpm.controller;

import com.itpm.dto.ApiResponse;
import com.itpm.dto.ProjectDTO;
import com.itpm.model.Project;
import com.itpm.service.ProjectService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目控制器
 */
@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> getAllProjects() {
        try {
            List<ProjectDTO> projects = projectService.findAll()
                    .stream()
                    .map(ProjectDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(projects));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/next-code")
    public ResponseEntity<ApiResponse<String>> getNextProjectCode() {
        try {
            String code = projectService.generateNextProjectCode();
            return ResponseEntity.ok(ApiResponse.success(code));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDTO>> getProjectById(@PathVariable Long id) {
        try {
            Project project = projectService.findById(id)
                    .orElseThrow(() -> new RuntimeException("项目不存在"));
            return ResponseEntity.ok(ApiResponse.success(ProjectDTO.fromEntity(project)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectDTO>> createProject(@Valid @RequestBody ProjectDTO request) {
        try {
            Project project = projectService.createProject(request);
            return ResponseEntity.ok(ApiResponse.success("项目创建成功", ProjectDTO.fromEntity(project)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDTO>> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDTO request) {
        try {
            Project project = projectService.updateProject(id, request);
            return ResponseEntity.ok(ApiResponse.success("项目更新成功", ProjectDTO.fromEntity(project)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok(ApiResponse.success("项目删除成功", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
