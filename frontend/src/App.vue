<template>
  <div id="app" class="app-container">
    <el-container>
      <el-aside width="200px" v-if="showSidebar" class="sidebar">
        <el-menu :default-active="activeMenu" class="el-menu-vertical-demo" @select="handleMenuSelect">
          <el-menu-item index="/dashboard">
            <span>仪表板</span>
          </el-menu-item>
          <el-menu-item index="/projects">
            <span>项目管理</span>
          </el-menu-item>
          <el-menu-item index="/tasks">
            <span>任务管理</span>
          </el-menu-item>
          <el-menu-item index="/wbs">
            <span>项目计划</span>
          </el-menu-item>
          <el-menu-item index="/members">
            <span>成员管理</span>
          </el-menu-item>
          <el-menu-item index="/approvals">
            <span>审批流程</span>
          </el-menu-item>
          <el-menu-item index="/reports">
            <span>报表统计</span>
          </el-menu-item>
          <el-menu-item index="/logout">
            <span>退出登录</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header v-if="showSidebar" class="header">
          <div class="header-content">
            <h1>IT项目管理系统</h1>
            <div class="user-info">
              <span>{{ currentUser }}</span>
            </div>
          </div>
        </el-header>

        <el-main class="main-content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const activeMenu = ref('/dashboard')
const currentUser = ref('用户')

const showSidebar = computed(() => {
  return route.path !== '/login'
})

watch(() => route.path, (newPath) => {
  if (newPath !== '/login') {
    activeMenu.value = newPath
  }
})

const handleMenuSelect = (index) => {
  if (index === '/logout') {
    localStorage.removeItem('token')
    router.push('/login')
  } else {
    router.push(index)
  }
}
</script>

<style scoped>
.app-container {
  width: 100%;
  min-height: 100vh;
}

.sidebar {
  background-color: #f0f2f5;
  border-right: 1px solid #ddd;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  padding: 0 20px;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.header h1 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.user-info {
  font-size: 14px;
  color: #666;
}

.main-content {
  background-color: #f5f7fa;
  padding: 20px;
  min-height: calc(100vh - 60px);
}
</style>
