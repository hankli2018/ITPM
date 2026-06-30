# 项目架构设计

## 系统架构

```
┌─────────────────────────────────────────────────────────────────┐
│                        浏览器 (Frontend)                         │
│                    Vue 3 + Vite + Element Plus                   │
└──────────────────────────────┬──────────────────────────────────┘
                               │ HTTP/HTTPS
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                        Nginx 反向代理                            │
│                    (生产环境 / API 路由)                         │
└──────────────────────────────┬──────────────────────────────────┘
                               │ HTTP
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Java Spring Boot 后端                         │
│               (REST API / JWT 认证 / 业务逻辑)                   │
│                                                                  │
│  ┌────────────────┐  ┌────────────────┐  ┌────────────────┐    │
│  │   Controller   │  │   Service      │  │  Repository    │    │
│  │                │  │                │  │                │    │
│  │ - Auth         │  │ - User         │  │ - JPA 查询      │    │
│  │ - Project      │  │ - Project      │  │ - 自定义方法     │    │
│  │ - Task         │  │ - Task         │  │                │    │
│  │ - User         │  │ - Approval     │  │                │    │
│  │ - Approval     │  │                │  │                │    │
│  └────────────────┘  └────────────────┘  └────────────────┘    │
│                                                                  │
│  ┌────────────────┐  ┌────────────────┐  ┌────────────────┐    │
│  │  Config        │  │   Security     │  │   Exception    │    │
│  │                │  │                │  │   Handler      │    │
│  │ - App Config   │  │ - JWT Filter   │  │ - Global       │    │
│  │ - Security     │  │ - Security     │  │   Handler      │    │
│  │ - Web MVC      │  │   Config       │  │ - Custom       │    │
│  └────────────────┘  └────────────────┘  └────────────────┘    │
└──────────────────────────────┬──────────────────────────────────┘
                               │ JDBC
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                       MySQL 数据库                              │
│                    (关系型数据库 / InnoDB)                       │
│                                                                  │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐        │
│  │  users   │  │ projects │  │  tasks   │  │ approvals│        │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘        │
│                                                                  │
│  ┌──────────────────────┐  ┌──────────────────┐               │
│  │ project_members      │  │ 其他表结构       │               │
│  └──────────────────────┘  └──────────────────┘               │
└─────────────────────────────────────────────────────────────────┘
```

## 分层架构

### 1. 表现层 (Presentation Layer)
**技术**: Vue 3 + Vite + Element Plus

**职责**:
- 用户界面设计和交互
- 页面路由管理
- 表单验证和数据展示
- 状态管理 (Pinia)

**核心模块**:
- Components: 可复用组件库
- Views: 页面容器组件
- Stores: 全局状态管理
- Router: 路由配置

### 2. 业务逻辑层 (Service Layer)
**技术**: Spring Boot Service

**职责**:
- 处理复杂的业务逻辑
- 数据验证和转换
- 事务管理
- 业务规则实现

**核心模块**:
- UserService: 用户管理
- ProjectService: 项目管理
- TaskService: 任务管理
- AuthService: 认证授权
- ApprovalService: 审批流程

### 3. 数据访问层 (Persistence Layer)
**技术**: Spring Data JPA

**职责**:
- 数据库操作封装
- SQL 查询构建
- 事务处理
- 缓存管理

**核心模块**:
- Repository: 数据访问接口
- Entity: 实体映射
- Query: 自定义查询

### 4. 数据存储层 (Data Storage Layer)
**技术**: MySQL 8.0

**职责**:
- 持久化数据存储
- 数据一致性保证
- 性能优化
- 备份恢复

## 核心数据模型

### User (用户)
```
User
├── id: Long
├── username: String (唯一)
├── password: String (加密)
├── email: String (唯一)
├── realName: String
├── phone: String
├── role: Enum (ADMIN, PROJECT_MANAGER, DEVELOPER, VIEWER)
├── department: String
├── isActive: Boolean
├── createdAt: LocalDateTime
└── updatedAt: LocalDateTime
```

### Project (项目)
```
Project
├── id: Long
├── name: String
├── description: String
├── projectCode: String (唯一)
├── projectManager: User (外键)
├── status: Enum (PLANNING, ACTIVE, SUSPENDED, COMPLETED, CANCELLED)
├── startDate: LocalDateTime
├── endDate: LocalDateTime
├── budget: Double
├── actualCost: Double
├── progress: Integer (0-100)
├── createdAt: LocalDateTime
└── updatedAt: LocalDateTime
```

### Task (任务)
```
Task
├── id: Long
├── title: String
├── description: String
├── project: Project (外键)
├── assignedTo: User (外键)
├── status: Enum (PENDING, IN_PROGRESS, REVIEW, COMPLETED, CANCELLED)
├── priority: Enum (LOW, MEDIUM, HIGH, CRITICAL)
├── startDate: LocalDateTime
├── endDate: LocalDateTime
├── completionPercentage: Integer (0-100)
├── estimatedHours: Double
├── actualHours: Double
├── createdAt: LocalDateTime
└── updatedAt: LocalDateTime
```

### ProjectMember (项目成员)
```
ProjectMember
├── id: Long
├── project: Project (外键)
├── user: User (外键)
├── role: Enum (PROJECT_LEAD, DEVELOPER, TESTER, DESIGNER, OBSERVER)
├── joinedDate: LocalDateTime
└── createdAt: LocalDateTime
```

### Approval (审批)
```
Approval
├── id: Long
├── requestType: String
├── requestId: Long
├── description: String
├── requester: User (外键)
├── approver: User (外键)
├── status: Enum (PENDING, APPROVED, REJECTED, WITHDRAWN)
├── approvalComment: String
├── submittedAt: LocalDateTime
├── approvedAt: LocalDateTime
└── createdAt: LocalDateTime
```

## API 设计

### 认证接口
```
POST   /api/auth/login              登录
POST   /api/auth/register           注册
POST   /api/auth/logout             登出
GET    /api/auth/refresh            刷新 Token
```

### 用户接口
```
GET    /api/users                   获取所有用户
GET    /api/users/{id}              获取用户详情
POST   /api/users                   创建用户
PUT    /api/users/{id}              更新用户
DELETE /api/users/{id}              删除用户
```

### 项目接口
```
GET    /api/projects                获取所有项目
GET    /api/projects/{id}           获取项目详情
POST   /api/projects                创建项目
PUT    /api/projects/{id}           更新项目
DELETE /api/projects/{id}           删除项目
GET    /api/projects/{id}/members   获取项目成员
```

### 任务接口
```
GET    /api/tasks                   获取所有任务
GET    /api/tasks/{id}              获取任务详情
GET    /api/tasks/project/{projectId}  获取项目任务
POST   /api/tasks                   创建任务
PUT    /api/tasks/{id}              更新任务
DELETE /api/tasks/{id}              删除任务
```

## 安全设计

### 认证 (Authentication)
- JWT Token 认证
- Token 有效期: 24 小时
- Refresh Token 机制
- 安全的密码加密 (BCrypt)

### 授权 (Authorization)
- 基于角色的访问控制 (RBAC)
- 四个角色: ADMIN, PROJECT_MANAGER, DEVELOPER, VIEWER
- 细粒度权限控制

### 数据安全
- SQL 注入防护 (参数化查询)
- XSS 防护 (输入输出编码)
- CSRF 防护 (Token 验证)
- 密码加密存储

## 技术选型理由

| 技术 | 原因 |
|------|------|
| Vue 3 | 现代化框架，高效的响应式系统 |
| Spring Boot | 成熟的企业级框架，开发效率高 |
| MySQL | 关系型数据库，稳定可靠 |
| JWT | 无状态认证，适合分布式系统 |
| Pinia | 轻量级状态管理，易于使用 |
| Element Plus | 企业级组件库，风格统一 |

## 扩展性设计

### 1. 模块化架构
- 各功能模块相对独立
- 易于添加新功能
- 便于团队并行开发

### 2. 微服务准备
- Service 层已充分解耦
- 可轻松转换为微服务
- API 设计遵循 REST 规范

### 3. 缓存策略
- Redis 缓存常用数据
- 减少数据库查询
- 提升系统性能

### 4. 异步处理
- 使用消息队列 (RabbitMQ/Kafka)
- 异步任务处理
- 提高系统吞吐量
