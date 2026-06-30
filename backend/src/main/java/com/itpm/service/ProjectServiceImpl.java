package com.itpm.service;

import com.itpm.model.Project;
import com.itpm.model.User;
import com.itpm.dto.ProjectDTO;
import com.itpm.repository.ProjectRepository;
import com.itpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 项目业务实现
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public Project createProject(ProjectDTO dto) {
        User projectManager = null;
        if (dto.getProjectManagerId() != null) {
            projectManager = userRepository.findById(dto.getProjectManagerId())
                    .orElseThrow(() -> new RuntimeException("项目经理不存在"));
        }

        Project project = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .projectCode(dto.getProjectCode())
                .projectManager(projectManager)
                .status(Project.ProjectStatus.PLANNING)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .budget(dto.getBudget())
                .actualCost(0.0)
                .progress(0)
                .build();

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
