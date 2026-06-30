package com.itpm.service;

import com.itpm.model.User;
import com.itpm.dto.UserDTO;
import com.itpm.dto.CreateUserRequest;
import java.util.List;
import java.util.Optional;

/**
 * 用户业务接口
 */
public interface UserService {
    User createUser(CreateUserRequest request);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    User updateUser(Long id, CreateUserRequest request);
    void deleteUser(Long id);
    UserDTO getUserDTO(Long id);
}
