<template>
  <div class="dashboard-container">
    <h1>仪表板</h1>
    <el-row :gutter="20" class="card-row">
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-value">{{ stats.totalProjects }}</div>
          <div class="stat-label">总项目数</div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-value">{{ stats.activeProjects }}</div>
          <div class="stat-label">进行中的项目</div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-value">{{ stats.totalTasks }}</div>
          <div class="stat-label">任务总数</div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-value">{{ stats.completedTasks }}</div>
          <div class="stat-label">已完成任务</div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="chart-row">
      <el-col :xs="24" :md="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>项目进度分布</span>
            </div>
          </template>
          <div class="placeholder">项目进度图表将在此显示</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>任务状态分布</span>
            </div>
          </template>
          <div class="placeholder">任务状态图表将在此显示</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const stats = ref({
  totalProjects: 8,
  activeProjects: 5,
  totalTasks: 45,
  completedTasks: 28
})

onMounted(() => {
  // 从后端获取统计数据
})
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.dashboard-container h1 {
  margin-bottom: 30px;
  color: #333;
}

.card-row {
  margin-bottom: 30px;
}

.stat-card {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #667eea;
  margin-bottom: 10px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.chart-row {
  margin-bottom: 20px;
}

.chart-card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.placeholder {
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  background: #f5f7fa;
  border-radius: 4px;
}
</style>
