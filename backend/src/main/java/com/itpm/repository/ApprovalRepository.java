package com.itpm.repository;

import com.itpm.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 审批流程数据访问接口
 */
@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByStatus(Approval.ApprovalStatus status);
    List<Approval> findByApproverId(Long approverId);
    List<Approval> findByRequesterId(Long requesterId);
    List<Approval> findByRequestTypeAndRequestId(String requestType, Long requestId);
}
