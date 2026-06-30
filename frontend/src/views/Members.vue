<template>
  <div class="page-container">
    <h1>成员管理</h1>
    <el-table :data="memberList" stripe style="width: 100%;">
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="realName" label="姓名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="role" label="角色" />
      <el-table-column prop="department" label="部门" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { userApi } from '../api/index'

const memberList = ref([])

onMounted(() => {
  getMembers()
})

const getMembers = async () => {
  try {
    const response = await userApi.getAll()
    memberList.value = response.data || []
  } catch (error) {
    console.error('获取成员列表失败:', error)
  }
}
</script>

<style scoped>
.page-container {
  padding: 20px;
}
</style>
