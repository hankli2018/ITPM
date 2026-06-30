# 快速开始指南

## 一、环境准备

### 安装 Java
```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install openjdk-17-jdk

# 验证安装
java -version
```

### 安装 Node.js
```bash
# 使用 NVM 安装
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
nvm install 18
nvm use 18

# 验证安装
node -v
npm -v
```

### 安装 Maven
```bash
# Ubuntu/Debian
sudo apt-get install maven

# 验证安装
mvn -version
```

### 安装 MySQL
```bash
# Ubuntu/Debian
sudo apt-get install mysql-server

# 启动 MySQL
sudo systemctl start mysql

# 进入 MySQL
mysql -u root -p
```

## 二、数据库初始化

```sql
-- 创建数据库
CREATE DATABASE itpm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE itpm;

-- 创建用户表
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    real_name VARCHAR(100),
    phone VARCHAR(20),
    role ENUM('ADMIN', 'PROJECT_MANAGER', 'DEVELOPER', 'VIEWER') DEFAULT 'VIEWER',
    department VARCHAR(100),
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建项目表
CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    project_code VARCHAR(100) UNIQUE,
    project_manager_id BIGINT,
    status ENUM('PLANNING', 'ACTIVE', 'SUSPENDED', 'COMPLETED', 'CANCELLED') DEFAULT 'PLANNING',
    start_date TIMESTAMP NULL,
    end_date TIMESTAMP NULL,
    budget DECIMAL(10, 2),
    actual_cost DECIMAL(10, 2) DEFAULT 0,
    progress INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (project_manager_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建任务表
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    project_id BIGINT NOT NULL,
    assigned_to_id BIGINT,
    status ENUM('PENDING', 'IN_PROGRESS', 'REVIEW', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM',
    start_date TIMESTAMP NULL,
    end_date TIMESTAMP NULL,
    completion_percentage INT DEFAULT 0,
    estimated_hours DECIMAL(8, 2),
    actual_hours DECIMAL(8, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (assigned_to_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建项目成员表
CREATE TABLE project_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role ENUM('PROJECT_LEAD', 'DEVELOPER', 'TESTER', 'DESIGNER', 'OBSERVER') DEFAULT 'OBSERVER',
    joined_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE KEY unique_project_user (project_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建审批流程表
CREATE TABLE approvals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_type VARCHAR(100) NOT NULL,
    request_id BIGINT NOT NULL,
    description TEXT,
    requester_id BIGINT NOT NULL,
    approver_id BIGINT,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'WITHDRAWN') DEFAULT 'PENDING',
    approval_comment TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (requester_id) REFERENCES users(id),
    FOREIGN KEY (approver_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

## 三、后端启动

### 1. 配置数据库连接

编辑 `backend/src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/itpm?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### 2. 编译和运行

```bash
# 进入后端目录
cd backend

# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

后端将在 `http://localhost:8080/api` 启动

### 3. 验证后端

```bash
# 访问 Swagger UI
http://localhost:8080/api/swagger-ui.html

# 测试登录接口
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

## 四、前端启动

### 1. 安装依赖

```bash
cd frontend
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

前端将在 `http://localhost:5173` 启动

### 3. 构建生产版本

```bash
npm run build
```

## 五、使用 Docker 启动（可选）

### 前置需求
- Docker
- Docker Compose

### 启动所有服务

```bash
docker-compose up -d
```

### 查看日志

```bash
docker-compose logs -f
```

### 停止服务

```bash
docker-compose down
```

## 六、访问应用

打开浏览器访问：`http://localhost:5173`

默认用户：
- 用户名: admin
- 密码: admin123

## 七、常见问题解决

### 问题1: MySQL 连接失败
**解决**: 
1. 确保 MySQL 已启动
2. 检查数据库名称、用户名、密码是否正确
3. 检查防火墙是否已打开 3306 端口

### 问题2: 端口被占用
**解决**:
- 后端: 在 `application.properties` 中修改 `server.port`
- 前端: 在 `vite.config.js` 中修改 `server.port`

### 问题3: 前端无法连接后端
**解决**:
1. 确保后端已启动
2. 检查 `frontend/vite.config.js` 中的代理配置
3. 检查浏览器控制台错误信息

### 问题4: npm install 缓慢
**解决**:
```bash
npm install --registry https://registry.npmmirror.com
```

## 八、部署到生产环境

### 后端部署

1. 修改 `application.properties` 配置生产数据库
2. 构建 JAR 包：`mvn clean package`
3. 运行 JAR：`java -jar target/itpm-system-1.0.0.jar`

### 前端部署

1. 构建生产版本：`npm run build`
2. 将 `dist` 目录上传到 Web 服务器
3. 配置 Web 服务器指向 `dist/index.html`

## 九、获取帮助

- 查看项目文档: [README.md](../README.md)
- 后端开发指南: [BACKEND_GUIDE.md](./BACKEND_GUIDE.md)
- 前端开发指南: [FRONTEND_GUIDE.md](./FRONTEND_GUIDE.md)
