import axios from 'axios'

const API_BASE_URL = '/api'

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000
})

// 请求拦截器
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
apiClient.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

// 认证接口
export const authApi = {
  login: (username, password) => apiClient.post('/auth/login', { username, password }),
  register: (data) => apiClient.post('/auth/register', data)
}

// 用户接口
export const userApi = {
  getAll: () => apiClient.get('/users'),
  getById: (id) => apiClient.get(`/users/${id}`),
  update: (id, data) => apiClient.put(`/users/${id}`, data),
  delete: (id) => apiClient.delete(`/users/${id}`)
}

// 项目接口
export const projectApi = {
  getAll: () => apiClient.get('/projects'),
  getById: (id) => apiClient.get(`/projects/${id}`),
  create: (data) => apiClient.post('/projects', data),
  update: (id, data) => apiClient.put(`/projects/${id}`, data),
  delete: (id) => apiClient.delete(`/projects/${id}`)
}

// 任务接口
export const taskApi = {
  getAll: () => apiClient.get('/tasks'),
  getById: (id) => apiClient.get(`/tasks/${id}`),
  getByProject: (projectId) => apiClient.get(`/projects/${projectId}/tasks`),
  create: (data) => apiClient.post('/tasks', data),
  update: (id, data) => apiClient.put(`/tasks/${id}`, data),
  delete: (id) => apiClient.delete(`/tasks/${id}`)
}

// WBS计划接口
export const wbsApi = {
  getTree: (projectId) => apiClient.get(`/wbs/project/${projectId}/tree`),
  getNodes: (projectId) => apiClient.get(`/wbs/project/${projectId}/nodes`),
  getById: (id) => apiClient.get(`/wbs/${id}`),
  getChildren: (parentId) => apiClient.get(`/wbs/${parentId}/children`),
  create: (data) => apiClient.post('/wbs', data),
  update: (id, data) => apiClient.put(`/wbs/${id}`, data),
  delete: (id) => apiClient.delete(`/wbs/${id}`),
  generateTasks: (data) => apiClient.post('/wbs/generate-tasks', data),
  getStatistics: (projectId) => apiClient.get(`/wbs/project/${projectId}/statistics`),
  validateCode: (projectId, wbsCode, excludeId) =>
    apiClient.get('/wbs/validate-code', { params: { projectId, wbsCode, excludeId } }),
  recalculate: (id) => apiClient.post(`/wbs/${id}/recalculate`)
}

export default apiClient
