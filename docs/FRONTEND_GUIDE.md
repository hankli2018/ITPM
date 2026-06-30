## 前端开发指南

### 项目结构

```
frontend/
├── src/
│   ├── components/      # Vue 组件
│   │   ├── projects/    # 项目管理相关组件
│   │   ├── tasks/       # 任务管理相关组件
│   │   ├── members/     # 成员管理相关组件
│   │   ├── approval/    # 审批流程相关组件
│   │   └── common/      # 通用组件
│   ├── views/           # 页面组件
│   ├── stores/          # Pinia 状态管理
│   ├── api/             # API 调用
│   ├── router/          # 路由配置
│   ├── utils/           # 工具函数
│   ├── App.vue
│   └── main.js
├── public/              # 静态资源
├── index.html
├── vite.config.js
└── package.json
```

### 快速开始

1. **安装依赖**
   ```bash
   npm install
   ```

2. **启动开发服务器**
   ```bash
   npm run dev
   ```

3. **构建生产版本**
   ```bash
   npm run build
   ```

### 项目架构

#### 路由结构
```
/
├── /login              # 登录页
├── /dashboard          # 仪表板
├── /projects           # 项目列表
├── /projects/:id       # 项目详情
├── /tasks              # 任务列表
├── /members            # 成员管理
├── /approvals          # 审批流程
└── /reports            # 报表统计
```

#### 状态管理 (Pinia)
- `userStore` - 用户状态 (登录、用户信息等)
- `projectStore` - 项目状态
- `taskStore` - 任务状态

### API 集成

#### 创建新的API
```javascript
// src/api/index.js
export const newApi = {
  getAll: () => apiClient.get('/endpoint'),
  getById: (id) => apiClient.get(`/endpoint/${id}`),
  create: (data) => apiClient.post('/endpoint', data),
  update: (id, data) => apiClient.put(`/endpoint/${id}`, data),
  delete: (id) => apiClient.delete(`/endpoint/${id}`)
}
```

#### 在组件中使用
```vue
<script setup>
import { ref, onMounted } from 'vue'
import { userApi } from '../api/index'

const users = ref([])

onMounted(async () => {
  try {
    const response = await userApi.getAll()
    users.value = response.data
  } catch (error) {
    console.error('获取失败:', error)
  }
})
</script>
```

### 组件开发

#### 创建新组件
```vue
<template>
  <div class="component-container">
    <h1>{{ title }}</h1>
    <!-- 组件内容 -->
  </div>
</template>

<script setup>
import { ref } from 'vue'

const title = ref('组件标题')
</script>

<style scoped>
.component-container {
  padding: 20px;
}
</style>
```

#### 使用Element Plus
```vue
<template>
  <el-button type="primary">按钮</el-button>
  <el-input v-model="input" />
  <el-table :data="tableData" />
</template>

<script setup>
import { ref } from 'vue'

const input = ref('')
const tableData = ref([])
</script>
```

### 状态管理

#### 创建新的 Store
```javascript
// src/stores/example.js
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useExampleStore = defineStore('example', () => {
  const data = ref([])
  
  const getData = async () => {
    // 获取数据
  }
  
  return {
    data,
    getData
  }
})
```

#### 在组件中使用 Store
```vue
<script setup>
import { useExampleStore } from '../stores/example'

const store = useExampleStore()

const handleClick = () => {
  store.getData()
}
</script>
```

### 表单处理

#### 表单验证
```vue
<template>
  <el-form :model="form" :rules="rules" ref="formRef">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item>
      <el-button @click="handleSubmit">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref } from 'vue'

const form = ref({ username: '' })
const formRef = ref(null)

const rules = {
  username: [
    { required: true, message: '用户名不能为空', trigger: 'blur' }
  ]
}

const handleSubmit = async () => {
  await formRef.value.validate((valid) => {
    if (valid) {
      // 提交表单
    }
  })
}
</script>
```

### 最佳实践

1. **组件化开发** - 将UI拆分为小的可复用组件
2. **状态集中管理** - 使用Pinia管理全局状态
3. **错误处理** - 为所有API调用添加错误处理
4. **性能优化** - 使用 `computed` 和 `watch` 优化性能
5. **代码规范** - 保持一致的代码风格
6. **文件组织** - 按功能模块组织文件

### 常见问题

**Q: 如何处理登录状态？**
A: 使用 `userStore` 管理登录状态，在路由守卫中检查

**Q: 如何进行页面间的数据传递？**
A: 使用 URL 参数、Pinia 状态或路由 meta 字段

**Q: 如何调试前端代码？**
A: 使用浏览器开发者工具，或在代码中添加 `console.log`

**Q: 如何优化页面加载速度？**
A: 使用代码分割、懒加载和图片优化
