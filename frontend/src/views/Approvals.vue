<template>
  <div class="page-container">
    <h1>审批流程</h1>
    <el-table :data="approvalList" stripe style="width: 100%;">
      <el-table-column prop="requestType" label="申请类型" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="status" label="状态" />
      <el-table-column prop="submittedAt" label="提交时间" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const approvalList = ref([])

onMounted(() => {
  // 获取审批列表
})
</script>

<style scoped>
.page-container {
  padding: 20px;
}
</style>
