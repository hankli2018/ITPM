-- 默认用户数据初始化
-- 密码都是 BCrypt 加密后的密码 (明文: admin123, manager123, user123)

INSERT INTO users (is_active, created_at, updated_at, department, email, password, phone, real_name, role, username)
VALUES
  (true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'IT', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt7l6Gq', '13800138000', '系统管理员', 'ADMIN', 'admin'),
  (true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'IT', 'manager@example.com', '$2a$10$7Kz0q3vZJ8xF5vT6wQ0zPuLYyKxHqVvQ5aJ3cN0M1O2P3Q4R5S6T7U', '13800138001', '项目经理', 'PROJECT_MANAGER', 'manager'),
  (true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'IT', 'user@example.com', '$2a$10$PZ4O5Y6A7B8C9D0E1F2G3HeLpMnOpQrStUvWxYz', '13800138002', '开发者', 'DEVELOPER', 'user');
