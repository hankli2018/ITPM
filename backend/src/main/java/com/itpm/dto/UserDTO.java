package com.itpm.dto;

import com.itpm.model.User;

/**
 * 用户 DTO
 */
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String realName;
    private String phone;
    private String role;
    private String department;
    private Boolean isActive;

    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String username, String email, String realName, String phone,
                   String role, String department, Boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.realName = realName;
        this.phone = phone;
        this.role = role;
        this.department = department;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

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

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRealName(user.getRealName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole().toString());
        dto.setDepartment(user.getDepartment());
        dto.setIsActive(user.getIsActive());
        return dto;
    }
}
