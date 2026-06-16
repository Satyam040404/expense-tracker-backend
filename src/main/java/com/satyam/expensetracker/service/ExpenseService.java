package com.satyam.expensetracker.service;

import com.satyam.expensetracker.entity.Expense;
import com.satyam.expensetracker.entity.User;
import com.satyam.expensetracker.repository.ExpenseRepository;
import com.satyam.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.satyam.expensetracker.exception.ResourceNotFoundException;
import com.satyam.expensetracker.security.CurrentUserHolder;
import java.util.List;
import com.satyam.expensetracker.exception.UnauthorizedException;
import java.util.Map;
import java.util.HashMap;

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
    public Expense addExpense(Expense expense) {

        String email = CurrentUserHolder.getEmail();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        expense.setUser(user);

        return expenseRepository.save(expense);
    }
    public Expense updateExpense(
            Long expenseId,
            Expense updatedExpense) {

        Expense existingExpense = expenseRepository
                .findById(expenseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found"));

        String email = CurrentUserHolder.getEmail();

        if (!existingExpense.getUser()
                .getEmail()
                .equals(email)) {

            throw new UnauthorizedException(
                    "You cannot update this expense");
        }

        existingExpense.setTitle(
                updatedExpense.getTitle());
        existingExpense.setAmount(
                updatedExpense.getAmount());
        existingExpense.setCategory(
                updatedExpense.getCategory());
        existingExpense.setDate(
                updatedExpense.getDate());
        existingExpense.setDescription(
                updatedExpense.getDescription());

        return expenseRepository.save(existingExpense);
    }
    public void deleteExpense(Long expenseId) {

        Expense expense = expenseRepository
                .findById(expenseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found"));

        String email = CurrentUserHolder.getEmail();

        if (!expense.getUser()
                .getEmail()
                .equals(email)) {

            throw new UnauthorizedException(
                    "You cannot delete this expense");
        }

        expenseRepository.delete(expense);
    }
    public Map<String, Double> getExpenseSummary() {

        String email = CurrentUserHolder.getEmail();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        List<Object[]> results =
                expenseRepository.getExpenseSummaryByUser(
                        user.getId());

        Map<String, Double> summary =
                new HashMap<>();

        for (Object[] row : results) {

            String category = (String) row[0];


            Double total = 0.0;

            if (row[1] != null) {
                total = ((Number) row[1]).doubleValue();
            }
            summary.put(category, total);
        }

        return summary;
    }
    public List<Expense> getExpensesByCategory(
            String category) {

        String email = CurrentUserHolder.getEmail();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return expenseRepository
                .findByUserIdAndCategory(
                        user.getId(),
                        category
                );
    }
    public List<Expense> searchExpenses(
            String keyword) {

        String email = CurrentUserHolder.getEmail();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return expenseRepository
                .findByUserIdAndTitleContainingIgnoreCase(
                        user.getId(),
                        keyword
                );
    }
}
