package com.satyam.expensetracker.service;

import com.satyam.expensetracker.entity.User;
import com.satyam.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.satyam.expensetracker.exception.EmailAlreadyExistsException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        return userRepository.save(user);
    }
}