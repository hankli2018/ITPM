package com.itpm.service;

import com.itpm.model.Project;
import com.itpm.model.User;
import com.itpm.dto.ProjectDTO;
import com.itpm.repository.ProjectRepository;
import com.itpm.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * 项目业务实现
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String generateNextProjectCode() {
        String yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String prefix = "IT-" + yearMonth + "-";
        List<String> codes = projectRepository.findMaxProjectCodeByPrefix(prefix);
        int nextSeq = 1;
        if (codes != null && !codes.isEmpty()) {
            String maxCode = codes.get(0);
            try {
                String seqStr = maxCode.substring(prefix.length());
                nextSeq = Integer.parseInt(seqStr) + 1;
            } catch (Exception e) {
                nextSeq = 1;
            }
        }
        return prefix + String.format("%04d", nextSeq);
    }

    @Override
    public Project createProject(ProjectDTO dto) {
        User projectManager = null;
        if (dto.getProjectManagerId() != null) {
            projectManager = userRepository.findById(dto.getProjectManagerId())
                    .orElseThrow(() -> new RuntimeException("项目经理不存在"));
        }

        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());

        String projectCode = dto.getProjectCode();
        if (projectCode == null || projectCode.trim().isEmpty()) {
            projectCode = generateNextProjectCode();
        }
        project.setProjectCode(projectCode);

        project.setProjectManager(projectManager);
        project.setStatus(Project.ProjectStatus.PLANNING);
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setBudget(dto.getBudget());
        project.setActualCost(0.0);
        project.setProgress(0);

        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project updateProject(Long id, ProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("项目不存在"));

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStatus(Project.ProjectStatus.valueOf(dto.getStatus()));
        project.setProgress(dto.getProgress());
        project.setActualCost(dto.getActualCost());

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> findByProjectManager(Long managerId) {
        return projectRepository.findByProjectManagerId(managerId);
    }

    @Override
    public List<Project> findByStatus(Project.ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }
}
