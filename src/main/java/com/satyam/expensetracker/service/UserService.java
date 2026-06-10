package com.satyam.expensetracker.service;

import com.satyam.expensetracker.entity.User;
import com.satyam.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.satyam.expensetracker.exception.EmailAlreadyExistsException;
import com.satyam.expensetracker.dto.UserRequestDTO;
import com.satyam.expensetracker.dto.UserResponseDTO;
import com.satyam.expensetracker.entity.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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