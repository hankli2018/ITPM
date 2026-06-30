package com.itpm.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户创建/更新请求 DTO
 */
public class CreateUserRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    private String realName;
    private String phone;
    private String role;
    private String department;

    // Constructors
    public CreateUserRequest() {}

    public CreateUserRequest(String username, String password, String email, String realName,
                            String phone, String role, String department) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.realName = realName;
        this.phone = phone;
        this.role = role;
        this.department = department;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
