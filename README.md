# IT项目管理系统 (ITPM)

## 项目概述

IT项目管理系统是一个企业级的项目管理平台，用于管理IT项目的生命周期。该系统提供了项目管理、任务管理、成员管理、审批流程、报表统计等核心功能。

## 技术栈

- **前端**: Vue 3 + Vite + Element Plus + Pinia
- **后端**: Java 17 + Spring Boot 3.1.5 + Spring Security
- **数据库**: MySQL 8.0
- **API文档**: Springdoc OpenAPI (Swagger)

## 项目结构

```
ITPM/
├── backend/                 # 后端项目
│   ├── src/main/
│   │   ├── java/com/itpm/
│   │   │   ├── controller/     # 控制器层
│   │   │   ├── service/        # 业务逻辑层
│   │   │   ├── repository/     # 数据访问层
│   │   │   ├── model/          # 实体模型
│   │   │   ├── dto/            # 数据传输对象
│   │   │   ├── config/         # 配置类
│   │   │   ├── security/       # 安全认证
│   │   │   ├── exception/      # 异常处理
│   │   │   └── utils/          # 工具类
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── pom.xml
├── frontend/                # 前端项目
│   ├── src/
│   │   ├── components/      # 组件
│   │   ├── views/           # 页面
│   │   ├── stores/          # Pinia 状态管理
│   │   ├── api/             # API 调用
│   │   ├── router/          # 路由配置
│   │   ├── utils/           # 工具函数
│   │   ├── App.vue
│   │   └── main.js
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
├── docs/                    # 项目文档
├── docker-compose.yml       # Docker 编排
└── README.md               # 项目说明
```

## 核心功能模块

### 1. 项目管理
- 创建、编辑、删除项目
- 项目进度跟踪
- 预算管理
- 项目成员分配

### 2. 任务管理
- 创建和分配任务
- 任务状态管理 (待开始、进行中、审核中、已完成、已取消)
- 任务优先级设置
- 工时统计

### 3. 成员管理
- 用户注册和登录
- 用户角色管理 (管理员、项目经理、开发者、观察者)
- 团队成员管理
- 权限控制

### 4. 审批流程
- 审批申请流程
- 多级审批
- 审批历史记录
- 自动化流程

### 5. 报表统计
- 项目完成情况
- 任务统计
- 工时统计
- 资源使用情况

## 快速开始

### 前置需求
- Java 17+
- Node.js 16+
- MySQL 8.0+

### 后端启动

1. 进入后端目录
```bash
cd backend
```

2. 编译项目
```bash
mvn clean package
```

3. 运行应用
```bash
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080/api` 启动

### 前端启动

1. 进入前端目录
```bash
cd frontend
```

2. 安装依赖
```bash
npm install
```

3. 启动开发服务器
```bash
npm run dev
```

前端将在 `http://localhost:5173` 启动

### 使用 Docker 启动

```bash
docker-compose up -d
```

## 数据库初始化

系统启动时会自动创建数据库表。可选择运行以下 SQL 脚本初始化数据：

```sql
-- 具体初始化脚本见 backend/src/main/resources/schema.sql
```

## API 文档

启动后端后，可以访问 Swagger UI：
- http://localhost:8080/api/swagger-ui.html

## 默认用户

系统初始化时会创建以下默认用户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | ADMIN |
| manager | manager123 | PROJECT_MANAGER |
| user | user123 | DEVELOPER |

## 环境配置

### 后端配置

编辑 `backend/src/main/resources/application.properties` 配置数据库连接：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/itpm
spring.datasource.username=root
spring.datasource.password=root
jwt.secret=your-secret-key
jwt.expiration=86400000
```

### 前端配置

编辑 `frontend/vite.config.js` 配置 API 代理：

```javascript
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true
  }
}
```

## 开发规范

### 后端
- 使用 Maven 管理依赖
- 遵循 Spring Boot 最佳实践
- 使用 Lombok 减少样板代码
- 统一异常处理

### 前端
- 使用 Vue 3 Composition API
- 使用 Pinia 管理全局状态
- 使用 Element Plus 组件库
- RESTful API 调用

## 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 常见问题

### Q: 如何添加新的用户角色？
A: 在 `User.java` 中的 `UserRole` 枚举中添加新的角色值，然后在 `SecurityConfig` 中配置权限。

### Q: 如何扩展审批流程？
A: 在 `Approval` 模型中定义新的审批类型，然后在相应的 Service 中实现审批逻辑。

### Q: 如何部署到生产环境？
A: 修改 `application.properties` 中的数据库连接信息，使用 Maven 构建 jar 包，然后部署到服务器。

## 许可证

MIT License

## 联系方式

如有问题或建议，请联系开发团队。
