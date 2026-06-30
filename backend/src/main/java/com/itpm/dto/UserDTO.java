package com.itpm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.itpm.model.User;

/**
 * 用户 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String realName;
    private String phone;
    private String role;
    private String department;
    private Boolean isActive;

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .realName(user.getRealName())
                .phone(user.getPhone())
                .role(user.getRole().toString())
                .department(user.getDepartment())
                .isActive(user.getIsActive())
                .build();
    }
}
