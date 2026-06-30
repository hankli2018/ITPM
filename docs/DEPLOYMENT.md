# 部署指南

## 系统需求

- **操作系统**: Linux (Ubuntu 18.04+) 或 Windows Server 2016+
- **Java**: OpenJDK 17 或 Oracle JDK 17+
- **MySQL**: 8.0+
- **Nginx**: 1.18+ (用于反向代理)

## 一、生产环境部署

### 1. 后端部署

#### 步骤1: 编译项目
```bash
cd backend
mvn clean package -DskipTests -Pprod
```

#### 步骤2: 配置生产环境
创建 `application-prod.properties` 文件：

```properties
spring.datasource.url=jdbc:mysql://db-server:3306/itpm?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.username=itpm_user
spring.datasource.password=strong_password_here
spring.jpa.hibernate.ddl-auto=validate
spring.profiles.active=prod
server.port=8080
jwt.secret=production-secret-key-must-be-very-long-and-secure
jwt.expiration=86400000
logging.level.com.itpm=INFO
```

#### 步骤3: 部署 JAR
```bash
# 复制 JAR 到服务器
scp target/itpm-system-1.0.0.jar user@server:/opt/itpm/

# 在服务器上运行
cd /opt/itpm
java -jar itpm-system-1.0.0.jar --spring.profiles.active=prod
```

#### 步骤4: 配置 Systemd 服务
创建 `/etc/systemd/system/itpm.service`:

```ini
[Unit]
Description=ITPM Application
After=network.target

[Service]
Type=simple
User=itpm
ExecStart=/usr/bin/java -Xmx1024m -Xms1024m -jar /opt/itpm/itpm-system-1.0.0.jar --spring.profiles.active=prod
Restart=on-failure
RestartSec=30

[Install]
WantedBy=multi-user.target
```

启动服务：
```bash
systemctl start itpm
systemctl enable itpm
systemctl status itpm
```

### 2. 前端部署

#### 步骤1: 构建项目
```bash
cd frontend
npm install
npm run build
```

#### 步骤2: 配置 Nginx
创建 `/etc/nginx/sites-available/itpm`:

```nginx
upstream backend {
    server localhost:8080;
}

server {
    listen 80;
    server_name your-domain.com;

    # 重定向到 HTTPS
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl http2;
    server_name your-domain.com;

    ssl_certificate /etc/ssl/certs/your-cert.crt;
    ssl_certificate_key /etc/ssl/private/your-key.key;

    # 静态文件
    location / {
        root /var/www/itpm/dist;
        index index.html;
        try_files $uri $uri/ /index.html;
    }

    # API 反向代理
    location /api/ {
        proxy_pass http://backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_buffering off;
    }

    # 压缩
    gzip on;
    gzip_types text/plain text/css text/javascript application/javascript application/json;
    gzip_min_length 1024;
}
```

启用站点：
```bash
ln -s /etc/nginx/sites-available/itpm /etc/nginx/sites-enabled/
nginx -t
systemctl restart nginx
```

#### 步骤3: 部署前端文件
```bash
# 复制构建的文件到 Web 目录
scp -r dist/* user@server:/var/www/itpm/dist/
```

### 3. MySQL 部署

#### 步骤1: 创建数据库和用户
```sql
CREATE DATABASE itpm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'itpm_user'@'localhost' IDENTIFIED BY 'strong_password_here';
GRANT ALL PRIVILEGES ON itpm.* TO 'itpm_user'@'localhost';
FLUSH PRIVILEGES;
```

#### 步骤2: 初始化数据
```bash
mysql -u itpm_user -p itpm < backend/src/main/resources/schema.sql
```

## 二、监控和日志

### 1. 日志位置
```bash
# 后端日志
tail -f /opt/itpm/logs/itpm.log

# Nginx 日志
tail -f /var/log/nginx/access.log
tail -f /var/log/nginx/error.log

# MySQL 日志
tail -f /var/log/mysql/error.log
```

### 2. 性能监控
```bash
# 检查 Java 进程
ps aux | grep java

# 检查内存使用
free -h

# 检查磁盘使用
df -h

# 检查 MySQL 连接
mysql -e "SHOW PROCESSLIST;"
```

## 三、备份和恢复

### 1. 数据库备份
```bash
# 完整备份
mysqldump -u itpm_user -p itpm > backup-$(date +%Y%m%d).sql

# 定时备份脚本
0 2 * * * /usr/bin/mysqldump -u itpm_user -p itpm > /backup/itpm-$(date +\%Y\%m\%d).sql
```

### 2. 应用程序备份
```bash
# 备份应用
tar -czf itpm-backup-$(date +%Y%m%d).tar.gz /opt/itpm/ /var/www/itpm/
```

### 3. 恢复备份
```bash
# 恢复数据库
mysql -u itpm_user -p itpm < backup-20240630.sql

# 恢复应用
tar -xzf itpm-backup-20240630.tar.gz -C /
```

## 四、安全加固

### 1. SSL/TLS 证书
使用 Let's Encrypt 获取免费证书：
```bash
sudo apt-get install certbot python3-certbot-nginx
sudo certbot certonly --nginx -d your-domain.com
```

### 2. 防火墙配置
```bash
# 允许 HTTP
sudo ufw allow 80

# 允许 HTTPS
sudo ufw allow 443

# 允许 SSH
sudo ufw allow 22

# 启用防火墙
sudo ufw enable
```

### 3. 修改默认密码
```bash
# 修改 MySQL root 密码
mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';"
```

## 五、故障排查

### 问题1: 应用无法启动
```bash
# 查看错误日志
journalctl -u itpm -n 50
```

### 问题2: 数据库连接失败
```bash
# 测试数据库连接
mysql -u itpm_user -p -h localhost -e "USE itpm; SELECT 1;"
```

### 问题3: 前端无法访问后端
```bash
# 检查 Nginx 配置
nginx -t

# 查看 Nginx 错误日志
tail -f /var/log/nginx/error.log

# 测试后端连接
curl http://localhost:8080/api/auth/login
```

## 六、性能优化

### 1. JVM 内存优化
```bash
# 在启动脚本中设置
java -Xms2048m -Xmx2048m -XX:+UseG1GC ...
```

### 2. 数据库优化
```sql
-- 添加索引
ALTER TABLE tasks ADD INDEX idx_project_id (project_id);
ALTER TABLE tasks ADD INDEX idx_assigned_to_id (assigned_to_id);
ALTER TABLE projects ADD INDEX idx_status (status);

-- 查看慢查询
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 2;
```

### 3. Nginx 缓存优化
```nginx
# 启用缓存
proxy_cache_path /var/cache/nginx levels=1:2 keys_zone=my_cache:10m;

location /api/ {
    proxy_cache my_cache;
    proxy_cache_valid 200 10m;
}
```

## 七、定期维护

- 每日备份数据库
- 每周检查日志
- 每月更新系统补丁
- 每季度性能审查
