<template>
  <div class="page-container">
    <h1>报表统计</h1>
    <el-row :gutter="20">
      <el-col :xs="24" :md="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>项目完成情况</span>
            </div>
          </template>
          <div class="chart-placeholder">图表将在此显示</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>任务统计</span>
            </div>
          </template>
          <div class="chart-placeholder">图表将在此显示</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
</script>

<style scoped>
.page-container {
  padding: 20px;
}

.chart-placeholder {
  height: 400px;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
}
</style>
