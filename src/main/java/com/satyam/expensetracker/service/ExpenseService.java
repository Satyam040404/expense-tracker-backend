package com.satyam.expensetracker.service;

import com.satyam.expensetracker.entity.Expense;
import com.satyam.expensetracker.entity.User;
import com.satyam.expensetracker.repository.ExpenseRepository;
import com.satyam.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.satyam.expensetracker.exception.ResourceNotFoundException;
import com.satyam.expensetracker.entity.User;
import com.satyam.expensetracker.repository.UserRepository;
import com.satyam.expensetracker.security.CurrentUserHolder;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }
    public List<Expense> getMyExpenses() {

        String email = CurrentUserHolder.getEmail();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return expenseRepository.findByUserId(
                user.getId()
        );
    }
    public Expense addExpense(Long userId, Expense expense) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }
    public Expense updateExpense(Long expenseId, Expense updatedExpense) {

        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        existingExpense.setTitle(updatedExpense.getTitle());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setDate(updatedExpense.getDate());
        existingExpense.setDescription(updatedExpense.getDescription());

        return expenseRepository.save(existingExpense);
    }
    public void deleteExpense(Long expenseId) {

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);
    }
}
