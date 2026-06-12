package com.satyam.expensetracker.controller;

import com.satyam.expensetracker.entity.User;
import com.satyam.expensetracker.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.satyam.expensetracker.dto.UserRequestDTO;
import com.satyam.expensetracker.dto.UserResponseDTO;
import jakarta.validation.Valid;
import com.satyam.expensetracker.dto.LoginRequestDTO;
import com.satyam.expensetracker.dto.LoginResponseDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

        return userService.registerUser(userRequestDTO);
    }
    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO request) {

        return userService.login(request);
    }
}