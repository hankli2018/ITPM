<template>
  <div class="page-container">
    <h1>项目详情</h1>
    <el-card v-if="project">
      <template #header>
        <div class="card-header">
          <span>{{ project.name }}</span>
        </div>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="项目代码">{{ project.projectCode }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ project.status }}</el-descriptions-item>
        <el-descriptions-item label="描述" :span="2">{{ project.description }}</el-descriptions-item>
        <el-descriptions-item label="开始日期">{{ project.startDate }}</el-descriptions-item>
        <el-descriptions-item label="结束日期">{{ project.endDate }}</el-descriptions-item>
        <el-descriptions-item label="预算">{{ project.budget }}</el-descriptions-item>
        <el-descriptions-item label="实际成本">{{ project.actualCost }}</el-descriptions-item>
        <el-descriptions-item label="进度">{{ project.progress }}%</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { projectApi } from '../api/index'

const route = useRoute()
const project = ref(null)

onMounted(() => {
  getProjectDetail()
})

const getProjectDetail = async () => {
  try {
    const id = route.params.id
    const response = await projectApi.getById(id)
    project.value = response.data
  } catch (error) {
    console.error('获取项目详情失败:', error)
  }
}
</script>

<style scoped>
.page-container {
  padding: 20px;
}
</style>
