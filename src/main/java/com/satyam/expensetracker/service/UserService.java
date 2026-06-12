package com.satyam.expensetracker.service;

import com.satyam.expensetracker.entity.User;
import com.satyam.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.satyam.expensetracker.exception.EmailAlreadyExistsException;
import com.satyam.expensetracker.dto.UserRequestDTO;
import com.satyam.expensetracker.dto.UserResponseDTO;
import com.satyam.expensetracker.entity.User;
import com.satyam.expensetracker.dto.LoginRequestDTO;
import com.satyam.expensetracker.dto.LoginResponseDTO;
import com.satyam.expensetracker.security.JwtService;
import com.satyam.expensetracker.exception.InvalidCredentialsException;
import com.satyam.expensetracker.exception.ResourceNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getPassword() == null ||
                !user.getPassword().equals(request.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponseDTO(token);
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

        if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }
}