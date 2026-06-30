<template>
  <div class="projects-container">
    <div class="header">
      <h1>项目管理</h1>
      <el-button type="primary" @click="showCreateDialog">+ 新建项目</el-button>
    </div>

    <el-table :data="projectList" stripe style="width: 100%; margin-bottom: 20px" v-loading="loading">
      <el-table-column prop="name" label="项目名称" width="200" />
      <el-table-column prop="projectCode" label="项目代码" width="150" />
      <el-table-column prop="status" label="状态" width="100" />
      <el-table-column prop="progress" label="进度" width="100">
        <template #default="scope">
          <el-progress :percentage="scope.row.progress" />
        </template>
      </el-table-column>
      <el-table-column prop="startDate" label="开始日期" width="150" />
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button type="primary" size="small" @click="viewProject(scope.row)">查看</el-button>
          <el-button size="small" @click="deleteProject(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新建项目对话框 -->
    <el-dialog v-model="createDialogVisible" title="新建项目">
      <el-form :model="newProject" label-width="100px">
        <el-form-item label="项目名称">
          <el-input v-model="newProject.name" />
        </el-form-item>
        <el-form-item label="项目代码">
          <el-input v-model="newProject.projectCode" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="newProject.description" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="createProject">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { projectApi } from '../api/index'

const router = useRouter()
const projectList = ref([])
const loading = ref(false)
const createDialogVisible = ref(false)
const newProject = ref({
  name: '',
  projectCode: '',
  description: ''
})

onMounted(() => {
  getProjects()
})

const getProjects = async () => {
  loading.value = true
  try {
    const response = await projectApi.getAll()
    projectList.value = response.data || []
  } catch (error) {
    ElMessage.error('获取项目列表失败')
  } finally {
    loading.value = false
  }
}

const showCreateDialog = () => {
  createDialogVisible.value = true
}

const createProject = async () => {
  try {
    await projectApi.create(newProject.value)
    ElMessage.success('项目创建成功')
    createDialogVisible.value = false
    newProject.value = { name: '', projectCode: '', description: '' }
    getProjects()
  } catch (error) {
    ElMessage.error('项目创建失败')
  }
}

const viewProject = (project) => {
  router.push(`/projects/${project.id}`)
}

const deleteProject = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除此项目吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await projectApi.delete(id)
    ElMessage.success('项目删除成功')
    getProjects()
  } catch (error) {
    ElMessage.error('项目删除失败')
  }
}
</script>

<style scoped>
.projects-container {
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
