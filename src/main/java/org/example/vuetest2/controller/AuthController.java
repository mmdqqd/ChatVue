package org.example.vuetest2.controller;

import lombok.RequiredArgsConstructor;
import org.example.vuetest2.common.Result;
import org.example.vuetest2.dto.LoginDTO;
import org.example.vuetest2.dto.RegisterDTO;
import org.example.vuetest2.entity.User;
import org.example.vuetest2.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginDTO loginDTO) {
        try {
            User user = authService.login(loginDTO);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody RegisterDTO registerDTO) {
        try {
            User user = authService.register(registerDTO);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
} 