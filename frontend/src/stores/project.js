import { defineStore } from 'pinia'
import { ref } from 'vue'
import { projectApi } from '../api/index'

export const useProjectStore = defineStore('project', () => {
  const projects = ref([])
  const currentProject = ref(null)
  const loading = ref(false)

  const getAll = async () => {
    loading.value = true
    try {
      const response = await projectApi.getAll()
      projects.value = response.data
      return response.data
    } catch (error) {
      console.error('获取项目列表失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  const getById = async (id) => {
    try {
      const response = await projectApi.getById(id)
      currentProject.value = response.data
      return response.data
    } catch (error) {
      console.error('获取项目详情失败:', error)
      throw error
    }
  }

  const create = async (projectData) => {
    try {
      const response = await projectApi.create(projectData)
      projects.value.push(response.data)
      return response.data
    } catch (error) {
      console.error('创建项目失败:', error)
      throw error
    }
  }

  const update = async (id, projectData) => {
    try {
      const response = await projectApi.update(id, projectData)
      const index = projects.value.findIndex(p => p.id === id)
      if (index !== -1) {
        projects.value[index] = response.data
      }
      return response.data
    } catch (error) {
      console.error('更新项目失败:', error)
      throw error
    }
  }

  const deleteProject = async (id) => {
    try {
      await projectApi.delete(id)
      projects.value = projects.value.filter(p => p.id !== id)
    } catch (error) {
      console.error('删除项目失败:', error)
      throw error
    }
  }

  return {
    projects,
    currentProject,
    loading,
    getAll,
    getById,
    create,
    update,
    deleteProject
  }
})
