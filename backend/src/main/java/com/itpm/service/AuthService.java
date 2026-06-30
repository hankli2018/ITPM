package com.itpm.service;

import com.itpm.dto.LoginRequest;
import com.itpm.dto.LoginResponse;

/**
 * 认证业务接口
 */
public interface AuthService {
    LoginResponse login(LoginRequest request);
    String generateToken(String username);
    String getUsernameFromToken(String token);
    boolean validateToken(String token);
}
