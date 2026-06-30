<template>
  <div class="tasks-container">
    <div class="header">
      <h1>任务管理</h1>
      <el-button type="primary">+ 新建任务</el-button>
    </div>

    <el-table :data="taskList" stripe style="width: 100%; margin-bottom: 20px">
      <el-table-column prop="title" label="任务标题" width="200" />
      <el-table-column prop="status" label="状态" width="100" />
      <el-table-column prop="priority" label="优先级" width="100" />
      <el-table-column prop="completionPercentage" label="完成度" width="100">
        <template #default="scope">
          <el-progress :percentage="scope.row.completionPercentage" />
        </template>
      </el-table-column>
      <el-table-column prop="endDate" label="截止日期" width="150" />
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button type="primary" size="small">编辑</el-button>
          <el-button size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { taskApi } from '../api/index'

const taskList = ref([])

onMounted(() => {
  getTasks()
})

const getTasks = async () => {
  try {
    const response = await taskApi.getAll()
    taskList.value = response.data || []
  } catch (error) {
    console.error('获取任务列表失败:', error)
  }
}
</script>

<style scoped>
.tasks-container {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header h1 {
  margin: 0;
  color: #333;
}
</style>
