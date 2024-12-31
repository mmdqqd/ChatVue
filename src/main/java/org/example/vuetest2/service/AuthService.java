package org.example.vuetest2.service;

import org.example.vuetest2.dto.LoginDTO;
import org.example.vuetest2.dto.RegisterDTO;
import org.example.vuetest2.entity.User;

public interface AuthService {
    User login(LoginDTO loginDTO);
    User register(RegisterDTO registerDTO);
} 