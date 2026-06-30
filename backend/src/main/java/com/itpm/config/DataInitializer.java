package com.itpm.config;

import com.itpm.model.User;
import com.itpm.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 数据初始化配置
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 确保管理员用户存在且密码正确
            User admin = userRepository.findByUsername("admin")
                    .orElseGet(() -> {
                        User newAdmin = new User();
                        newAdmin.setUsername("admin");
                        newAdmin.setEmail("admin@example.com");
                        newAdmin.setRealName("系统管理员");
                        newAdmin.setRole(User.UserRole.ADMIN);
                        newAdmin.setIsActive(true);
                        System.out.println(">>> 管理员用户创建成功");
                        return userRepository.save(newAdmin);
                    });
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            userRepository.save(admin);
            System.out.println(">>> 管理员用户初始化完成: admin / admin123");

            // 确保项目经理用户存在且密码正确
            User manager = userRepository.findByUsername("manager")
                    .orElseGet(() -> {
                        User newManager = new User();
                        newManager.setUsername("manager");
                        newManager.setEmail("manager@example.com");
                        newManager.setRealName("项目经理");
                        newManager.setRole(User.UserRole.PROJECT_MANAGER);
                        newManager.setIsActive(true);
                        System.out.println(">>> 项目经理用户创建成功");
                        return userRepository.save(newManager);
                    });
            manager.setPassword(passwordEncoder.encode("manager123"));
            manager.setEmail("manager@example.com");
            userRepository.save(manager);
            System.out.println(">>> 项目经理用户初始化完成: manager / manager123");

            // 确保普通用户存在且密码正确
            User user = userRepository.findByUsername("user")
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername("user");
                        newUser.setEmail("user@example.com");
                        newUser.setRealName("开发者");
                        newUser.setRole(User.UserRole.DEVELOPER);
                        newUser.setIsActive(true);
                        System.out.println(">>> 普通用户创建成功");
                        return userRepository.save(newUser);
                    });
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEmail("user@example.com");
            userRepository.save(user);
            System.out.println(">>> 普通用户初始化完成: user / user123");
        };
    }
}
