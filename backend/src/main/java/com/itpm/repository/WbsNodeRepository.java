package com.itpm.repository;

import com.itpm.model.WbsNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * WBS节点数据访问接口
 */
@Repository
public interface WbsNodeRepository extends JpaRepository<WbsNode, Long> {

    /**
     * 查询项目下的所有WBS节点
     */
    List<WbsNode> findByProjectIdOrderBySortOrderAsc(Long projectId);

    /**
     * 查询父节点下的子节点
     */
    List<WbsNode> findByParentIdOrderBySortOrderAsc(Long parentId);

    /**
     * 查询顶级节点（没有父节点的节点）
     */
    List<WbsNode> findByProjectIdAndParentIsNullOrderBySortOrderAsc(Long projectId);

    /**
     * 查询指定项目的所有叶节点（工作包和任务）
     */
    @Query("SELECT w FROM WbsNode w WHERE w.project.id = :projectId " +
           "AND w.nodeType IN (com.itpm.model.WbsNode$WbsNodeType.WORK_PACKAGE, com.itpm.model.WbsNode$WbsNodeType.TASK) " +
           "ORDER BY w.wbsCode")
    List<WbsNode> findLeafNodesByProjectId(@Param("projectId") Long projectId);

    /**
     * 根据WBS编码查询节点
     */
    WbsNode findByProjectIdAndWbsCode(Long projectId, String wbsCode);

    /**
     * 查询指定节点的所有后代节点
     */
    @Query(value = "WITH RECURSIVE wbs_tree AS (" +
                   "  SELECT * FROM wbs_nodes WHERE id = :nodeId " +
                   "  UNION ALL " +
                   "  SELECT w.* FROM wbs_nodes w " +
                   "  INNER JOIN wbs_tree wt ON w.parent_id = wt.id" +
                   ") SELECT * FROM wbs_tree", nativeQuery = true)
    List<WbsNode> findDescendants(@Param("nodeId") Long nodeId);

    /**
     * 查询项目的WBS树结构
     */
    @Query("SELECT w FROM WbsNode w WHERE w.project.id = :projectId ORDER BY w.level, w.sortOrder")
    List<WbsNode> findProjectWbsTree(@Param("projectId") Long projectId);

    /**
     * 统计项目的WBS节点数量
     */
    long countByProjectId(Long projectId);

    /**
     * 统计项目的WBS节点数量（按类型）
     */
    long countByProjectIdAndNodeType(Long projectId, WbsNode.WbsNodeType nodeType);

    /**
     * 检查节点是否有子节点
     */
    boolean existsByParentId(Long parentId);

    /**
     * 查询指定类型的所有节点
     */
    List<WbsNode> findByProjectIdAndNodeType(Long projectId, WbsNode.WbsNodeType nodeType);
}
