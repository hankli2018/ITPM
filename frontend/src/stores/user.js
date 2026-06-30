import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authApi } from '../api/index'

export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('token') || null)

  const isLoggedIn = computed(() => !!token.value)

  const login = async (username, password) => {
    try {
      const response = await authApi.login(username, password)
      token.value = response.data.token
      user.value = response.data.user
      localStorage.setItem('token', token.value)
      return response
    } catch (error) {
      throw error
    }
  }

  const logout = () => {
    user.value = null
    token.value = null
    localStorage.removeItem('token')
  }

  const register = async (userData) => {
    try {
      const response = await authApi.register(userData)
      return response
    } catch (error) {
      throw error
    }
  }

  return {
    user,
    token,
    isLoggedIn,
    login,
    logout,
    register
  }
})
