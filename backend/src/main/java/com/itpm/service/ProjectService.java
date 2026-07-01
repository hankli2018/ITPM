package com.itpm.service;

import com.itpm.model.Project;
import com.itpm.dto.ProjectDTO;
import java.util.List;
import java.util.Optional;

/**
 * 项目业务接口
 */
public interface ProjectService {
    Project createProject(ProjectDTO dto);
    Optional<Project> findById(Long id);
    List<Project> findAll();
    Project updateProject(Long id, ProjectDTO dto);
    void deleteProject(Long id);
    List<Project> findByProjectManager(Long managerId);
    List<Project> findByStatus(Project.ProjectStatus status);
    String generateNextProjectCode();
}
