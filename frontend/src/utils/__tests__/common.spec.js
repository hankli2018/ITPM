import { describe, it, expect } from 'vitest'
import { formatDate, isEmpty, deepClone } from '@/utils/common'

describe('Common Utils', () => {
  describe('formatDate', () => {
    it('应该格式化日期为默认格式', () => {
      const date = new Date('2024-06-30T12:30:45')
      const result = formatDate(date)
      expect(result).toContain('2024')
      expect(result).toContain('06')
      expect(result).toContain('30')
    })

    it('应该处理 null 值', () => {
      const result = formatDate(null)
      expect(result).toBe('')
    })
  })

  describe('isEmpty', () => {
    it('应该检测空值', () => {
      expect(isEmpty(null)).toBe(true)
      expect(isEmpty(undefined)).toBe(true)
      expect(isEmpty('')).toBe(true)
      expect(isEmpty('test')).toBe(false)
      expect(isEmpty(0)).toBe(false)
    })
  })

  describe('deepClone', () => {
    it('应该深拷贝对象', () => {
      const original = { a: 1, b: { c: 2 } }
      const cloned = deepClone(original)
      
      expect(cloned).toEqual(original)
      expect(cloned).not.toBe(original)
      expect(cloned.b).not.toBe(original.b)
    })

    it('应该深拷贝数组', () => {
      const original = [1, [2, 3], 4]
      const cloned = deepClone(original)
      
      expect(cloned).toEqual(original)
      expect(cloned).not.toBe(original)
    })
  })
})
