## 后端开发指南

### 项目结构

```
backend/
├── src/main/java/com/itpm/
│   ├── controller/      # 控制器层 - 处理HTTP请求
│   ├── service/         # 业务逻辑层 - 处理业务逻辑
│   ├── repository/      # 数据访问层 - 数据库操作
│   ├── model/           # 实体类 - 数据库对应的Java类
│   ├── dto/             # 数据传输对象 - 前后端交互数据
│   ├── config/          # 配置类 - 应用配置
│   ├── security/        # 安全认证 - JWT等认证
│   ├── exception/       # 异常处理 - 自定义异常
│   └── utils/           # 工具类 - 通用工具方法
└── src/main/resources/
    ├── application.properties      # 主配置文件
    └── application-dev.properties  # 开发环境配置
```

### 快速开始

1. **创建数据库**
   ```sql
   CREATE DATABASE itpm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **修改数据库配置**
   编辑 `application.properties` 文件

3. **运行项目**
   ```bash
   mvn spring-boot:run
   ```

4. **API文档**
   访问 `http://localhost:8080/api/swagger-ui.html`

### 开发流程

#### 添加新的功能

1. **创建Model** (`model/`)
   - 定义数据库实体

2. **创建Repository** (`repository/`)
   - 继承 `JpaRepository`
   - 定义自定义查询方法

3. **创建Service** (`service/`)
   - 定义业务接口
   - 实现业务逻辑

4. **创建Controller** (`controller/`)
   - 定义API端点
   - 调用Service处理请求

5. **创建DTO** (`dto/`)
   - 定义请求/响应对象

### 常用注解

- `@Entity` - 标记为数据库实体
- `@RestController` - 标记为REST控制器
- `@Service` - 标记为业务服务类
- `@Repository` - 标记为数据访问层
- `@RequestMapping` - 映射HTTP请求
- `@PostMapping` - 映射POST请求
- `@GetMapping` - 映射GET请求
- `@Autowired` - 自动注入依赖

### 代码示例

#### 创建实体
```java
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
}
```

#### 创建Repository
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

#### 创建Service
```java
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
}
```

#### 创建Controller
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(UserDTO.fromEntity(user)));
    }
}
```

### 最佳实践

1. **分层设计** - 遵循三层架构
2. **异常处理** - 使用统一的异常处理器
3. **参数验证** - 使用 `@Valid` 注解验证请求参数
4. **日志记录** - 使用 SLF4J 记录日志
5. **代码注释** - 添加必要的代码注释
6. **单元测试** - 为关键业务逻辑编写测试用例

### 常见问题

**Q: 如何处理数据库事务？**
A: 在Service方法上添加 `@Transactional` 注解

**Q: 如何设置连接池？**
A: 在 `application.properties` 中配置 HikariCP

**Q: 如何记录操作日志？**
A: 在Controller中使用 `@Aspect` 创建日志切面
