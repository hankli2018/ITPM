<template>
  <div class="wbs-container">
    <div class="header">
      <div class="header-left">
        <h1>项目计划管理</h1>
        <el-select v-model="selectedProjectId" placeholder="选择项目" style="width: 240px; margin-left: 20px" @change="handleProjectChange">
          <el-option
            v-for="project in projectList"
            :key="project.id"
            :label="project.name"
            :value="project.id"
          />
        </el-select>
      </div>
      <div class="header-right">
        <el-button type="primary" @click="handleAddRoot" :disabled="!selectedProjectId">
          + 新建阶段
        </el-button>
        <el-button type="success" @click="handleGenerateTasks" :disabled="!selectedProjectId">
          生成项目任务
        </el-button>
      </div>
    </div>

    <!-- 统计卡片 -->
    <div class="stats-row" v-if="statistics">
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">{{ statistics.totalNodes || 0 }}</div>
          <div class="stat-label">总节点数</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">{{ statistics.phases || 0 }}</div>
          <div class="stat-label">阶段</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">{{ statistics.totalBudgetHours || 0 }}h</div>
          <div class="stat-label">预算工时</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">¥{{ statistics.totalBudgetCost || 0 }}</div>
          <div class="stat-label">预算成本</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">{{ statistics.overallProgress || 0 }}%</div>
          <div class="stat-label">整体进度</div>
        </div>
      </el-card>
    </div>

    <!-- WBS 树形表格 -->
    <el-table
      :data="wbsTree"
      row-key="id"
      border
      default-expand-all
      :tree-props="{ children: 'children' }"
      style="width: 100%; margin-top: 20px"
    >
      <el-table-column prop="wbsCode" label="WBS编码" width="120" />
      <el-table-column prop="name" label="任务名称" min-width="200">
        <template #default="scope">
          <div class="node-name">
            <el-tag :type="getNodeTypeTag(scope.row.nodeType)" size="small" style="margin-right: 8px">
              {{ getNodeTypeLabel(scope.row.nodeType) }}
            </el-tag>
            <span>{{ scope.row.name }}</span>
            <el-tag v-if="scope.row.isMilestone" type="warning" size="small" style="margin-left: 8px">
              里程碑
            </el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="getStatusTag(scope.row.status)" size="small">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="budgetHours" label="预算工时(h)" width="100" />
      <el-table-column prop="actualHours" label="实际工时(h)" width="100" />
      <el-table-column prop="budgetCost" label="预算成本" width="100" />
      <el-table-column label="完成度" width="150">
        <template #default="scope">
          <el-progress :percentage="scope.row.completionPercentage || 0" :stroke-width="12" />
        </template>
      </el-table-column>
      <el-table-column prop="assigneeName" label="负责人" width="100" />
      <el-table-column prop="startDate" label="开始日期" width="160">
        <template #default="scope">
          {{ formatDate(scope.row.startDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="endDate" label="结束日期" width="160">
        <template #default="scope">
          {{ formatDate(scope.row.endDate) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="240" fixed="right">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleAddChild(scope.row)">
            添加子项
          </el-button>
          <el-button type="warning" size="small" @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="resetForm"
    >
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="100px">
        <el-form-item label="节点类型" prop="nodeType">
          <el-select v-model="formData.nodeType" style="width: 100%">
            <el-option label="阶段" value="PHASE" />
            <el-option label="可交付成果" value="DELIVERABLE" />
            <el-option label="工作包" value="WORK_PACKAGE" />
            <el-option label="任务" value="TASK" />
          </el-select>
        </el-form-item>
        <el-form-item label="节点名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入节点名称" />
        </el-form-item>
        <el-form-item label="WBS编码">
          <el-input v-model="formData.wbsCode" placeholder="自动生成，可手动修改" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="formData.status" style="width: 100%">
            <el-option label="计划中" value="PLANNING" />
            <el-option label="进行中" value="IN_PROGRESS" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="暂停" value="ON_HOLD" />
            <el-option label="已取消" value="CANCELLED" />
          </el-select>
        </el-form-item>
        <el-form-item label="负责人">
          <el-select v-model="formData.assigneeId" placeholder="请选择负责人" style="width: 100%" clearable>
            <el-option
              v-for="user in userList"
              :key="user.id"
              :label="user.realName || user.username"
              :value="user.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker
            v-model="formData.startDate"
            type="datetime"
            placeholder="选择开始日期"
            style="width: 100%"
            value-format="YYYY-MM-DDTHH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker
            v-model="formData.endDate"
            type="datetime"
            placeholder="选择结束日期"
            style="width: 100%"
            value-format="YYYY-MM-DDTHH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="预算工时">
          <el-input-number v-model="formData.budgetHours" :min="0" :step="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="实际工时">
          <el-input-number v-model="formData.actualHours" :min="0" :step="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="预算成本">
          <el-input-number v-model="formData.budgetCost" :min="0" :step="100" style="width: 100%" />
        </el-form-item>
        <el-form-item label="完成度">
          <el-slider v-model="formData.completionPercentage" :min="0" :max="100" />
        </el-form-item>
        <el-form-item label="是否里程碑">
          <el-switch v-model="formData.isMilestone" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="formData.description" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { projectApi, wbsApi, userApi } from '../api/index'

const projectList = ref([])
const userList = ref([])
const selectedProjectId = ref(null)
const wbsTree = ref([])
const statistics = ref(null)
const dialogVisible = ref(false)
const dialogMode = ref('create')
const formRef = ref(null)
const currentParentId = ref(null)
const editId = ref(null)

const formData = ref({
  name: '',
  wbsCode: '',
  nodeType: 'TASK',
  status: 'PLANNING',
  assigneeId: null,
  startDate: null,
  endDate: null,
  budgetHours: 0,
  actualHours: 0,
  budgetCost: 0,
  completionPercentage: 0,
  isMilestone: false,
  description: ''
})

const formRules = {
  name: [{ required: true, message: '请输入节点名称', trigger: 'blur' }],
  nodeType: [{ required: true, message: '请选择节点类型', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

const dialogTitle = computed(() => {
  return dialogMode.value === 'create' ? '新建WBS节点' : '编辑WBS节点'
})

import { computed } from 'vue'

onMounted(() => {
  loadProjects()
  loadUsers()
})

const loadProjects = async () => {
  try {
    const response = await projectApi.getAll()
    projectList.value = response.data || []
    if (projectList.value.length > 0) {
      selectedProjectId.value = projectList.value[0].id
      loadWbsData()
    }
  } catch (error) {
    console.error('加载项目列表失败:', error)
  }
}

const loadUsers = async () => {
  try {
    const response = await userApi.getAll()
    userList.value = response.data || []
  } catch (error) {
    console.error('加载用户列表失败:', error)
  }
}

const handleProjectChange = () => {
  loadWbsData()
}

const loadWbsData = async () => {
  if (!selectedProjectId.value) return
  try {
    const [treeRes, statsRes] = await Promise.all([
      wbsApi.getTree(selectedProjectId.value),
      wbsApi.getStatistics(selectedProjectId.value)
    ])
    wbsTree.value = treeRes.data || []
    statistics.value = statsRes.data || null
  } catch (error) {
    console.error('加载WBS数据失败:', error)
  }
}

const handleAddRoot = () => {
  dialogMode.value = 'create'
  currentParentId.value = null
  formData.value.nodeType = 'PHASE'
  dialogVisible.value = true
}

const handleAddChild = (row) => {
  dialogMode.value = 'create'
  currentParentId.value = row.id
  formData.value.nodeType = getChildNodeType(row.nodeType)
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogMode.value = 'edit'
  editId.value = row.id
  currentParentId.value = row.parentId
  formData.value = {
    name: row.name,
    wbsCode: row.wbsCode,
    nodeType: row.nodeType,
    status: row.status,
    assigneeId: row.assigneeId,
    startDate: row.startDate,
    endDate: row.endDate,
    budgetHours: row.budgetHours || 0,
    actualHours: row.actualHours || 0,
    budgetCost: row.budgetCost || 0,
    completionPercentage: row.completionPercentage || 0,
    isMilestone: row.isMilestone || false,
    description: row.description || ''
  }
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除 "${row.name}" 吗？其子节点也会被删除。`,
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await wbsApi.delete(row.id)
      ElMessage.success('删除成功')
      loadWbsData()
    } catch (error) {
      ElMessage.error(error.response?.data?.message || '删除失败')
    }
  }).catch(() => {})
}

const handleSubmit = async () => {
  if (!formRef.value) return
  try {
    await formRef.value.validate()
  } catch (e) {
    return
  }

  try {
    if (dialogMode.value === 'create') {
      await wbsApi.create({
        projectId: selectedProjectId.value,
        parentId: currentParentId.value,
        ...formData.value
      })
      ElMessage.success('创建成功')
    } else {
      await wbsApi.update(editId.value, formData.value)
      ElMessage.success('更新成功')
    }
    dialogVisible.value = false
    loadWbsData()
  } catch (error) {
    ElMessage.error(error.response?.data?.message || '操作失败')
  }
}

const resetForm = () => {
  formData.value = {
    name: '',
    wbsCode: '',
    nodeType: 'TASK',
    status: 'PLANNING',
    assigneeId: null,
    startDate: null,
    endDate: null,
    budgetHours: 0,
    actualHours: 0,
    budgetCost: 0,
    completionPercentage: 0,
    isMilestone: false,
    description: ''
  }
  formRef.value?.resetFields()
}

const handleGenerateTasks = () => {
  ElMessageBox.confirm(
    '确定要将所有工作包和任务节点生成为项目任务吗？',
    '生成任务确认',
    {
      confirmButtonText: '确定生成',
      cancelButtonText: '取消',
      type: 'info'
    }
  ).then(async () => {
    try {
      const response = await wbsApi.generateTasks({
        wbsNodeIds: wbsTree.value.map(n => n.id),
        includeChildren: true
      })
      ElMessage.success(response.message || `成功生成 ${response.data?.length || 0} 个任务`)
    } catch (error) {
      ElMessage.error(error.response?.data?.message || '生成失败')
    }
  }).catch(() => {})
}

const getNodeTypeLabel = (type) => {
  const map = {
    PROJECT: '项目',
    PHASE: '阶段',
    DELIVERABLE: '可交付成果',
    WORK_PACKAGE: '工作包',
    TASK: '任务'
  }
  return map[type] || type
}

const getNodeTypeTag = (type) => {
  const map = {
    PHASE: '',
    DELIVERABLE: 'success',
    WORK_PACKAGE: 'warning',
    TASK: 'info'
  }
  return map[type] || ''
}

const getChildNodeType = (parentType) => {
  const map = {
    PROJECT: 'PHASE',
    PHASE: 'DELIVERABLE',
    DELIVERABLE: 'WORK_PACKAGE',
    WORK_PACKAGE: 'TASK',
    TASK: 'TASK'
  }
  return map[parentType] || 'TASK'
}

const getStatusLabel = (status) => {
  const map = {
    PLANNING: '计划中',
    IN_PROGRESS: '进行中',
    COMPLETED: '已完成',
    ON_HOLD: '暂停',
    CANCELLED: '已取消'
  }
  return map[status] || status
}

const getStatusTag = (status) => {
  const map = {
    PLANNING: 'info',
    IN_PROGRESS: 'warning',
    COMPLETED: 'success',
    ON_HOLD: 'info',
    CANCELLED: 'danger'
  }
  return map[status] || ''
}

const formatDate = (date) => {
  if (!date) return '-'
  const d = new Date(date)
  return d.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}
</script>

<style scoped>
.wbs-container {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-left {
  display: flex;
  align-items: center;
}

.header h1 {
  margin: 0;
  color: #333;
}

.stats-row {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
}

.stat-card {
  flex: 1;
  min-width: 120px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.node-name {
  display: flex;
  align-items: center;
}
</style>
