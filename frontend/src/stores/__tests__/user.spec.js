import { describe, it, expect, beforeEach, vi } from 'vitest'
import { useUserStore } from '@/stores/user'
import { setActivePinia, createPinia } from 'pinia'

describe('User Store', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it('应该初始化为未登录状态', () => {
    const store = useUserStore()
    expect(store.isLoggedIn).toBe(false)
    expect(store.user).toBe(null)
  })

  it('应该能够设置用户信息', () => {
    const store = useUserStore()
    const mockUser = {
      id: 1,
      username: 'testuser',
      email: 'test@example.com',
      role: 'DEVELOPER'
    }
    store.user = mockUser
    expect(store.user).toEqual(mockUser)
  })

  it('应该能够管理登录状态', () => {
    const store = useUserStore()
    expect(store.isLoggedIn).toBe(false)
    store.token = 'test-token'
    expect(store.isLoggedIn).toBe(true)
  })
})
