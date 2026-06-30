/**
 * 验证工具函数
 */

export function isEmail(email) {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return regex.test(email)
}

export function isPhone(phone) {
  const regex = /^1[3-9]\d{9}$/
  return regex.test(phone)
}

export function isUsername(username) {
  const regex = /^[a-zA-Z0-9_-]{3,16}$/
  return regex.test(username)
}

export function isPassword(password) {
  // 至少8个字符，包含字母和数字
  const regex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d@$!%*?&]{8,}$/
  return regex.test(password)
}

export function isUrl(url) {
  const regex = /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/
  return regex.test(url)
}
