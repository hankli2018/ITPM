package com.itpm.repository;

import com.itpm.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * 项目数据访问接口
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectCode(String projectCode);
    List<Project> findByProjectManagerId(Long projectManagerId);
    List<Project> findByStatus(Project.ProjectStatus status);

    @Query("SELECT p.projectCode FROM Project p WHERE p.projectCode LIKE :prefix% ORDER BY p.projectCode DESC")
    List<String> findMaxProjectCodeByPrefix(@Param("prefix") String prefix);
}
