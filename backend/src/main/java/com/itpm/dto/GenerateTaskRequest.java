package com.itpm.dto;

import java.util.List;

/**
 * 从WBS节点生成任务请求DTO
 */
public class GenerateTaskRequest {

    /**
     * 要生成任务的WBS节点ID列表
     */
    private List<Long> wbsNodeIds;

    /**
     * 是否同时生成子节点的任务
     */
    private Boolean includeChildren = true;

    public GenerateTaskRequest() {
        this.includeChildren = true;
    }

    public GenerateTaskRequest(List<Long> wbsNodeIds, Boolean includeChildren) {
        this.wbsNodeIds = wbsNodeIds;
        this.includeChildren = includeChildren;
    }

    // Getters
    public List<Long> getWbsNodeIds() { return wbsNodeIds; }
    public Boolean getIncludeChildren() { return includeChildren; }

    // Setters
    public void setWbsNodeIds(List<Long> wbsNodeIds) { this.wbsNodeIds = wbsNodeIds; }
    public void setIncludeChildren(Boolean includeChildren) { this.includeChildren = includeChildren; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private List<Long> wbsNodeIds;
        private Boolean includeChildren = true;

        public Builder wbsNodeIds(List<Long> wbsNodeIds) {
            this.wbsNodeIds = wbsNodeIds;
            return this;
        }

        public Builder includeChildren(Boolean includeChildren) {
            this.includeChildren = includeChildren;
            return this;
        }

        public GenerateTaskRequest build() {
            return new GenerateTaskRequest(wbsNodeIds, includeChildren);
        }
    }
}
