package com.satyam.expensetracker.controller;

import com.satyam.expensetracker.entity.Expense;
import com.satyam.expensetracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense addExpense(
            @RequestBody Expense expense) {

        return expenseService.addExpense(expense);
    }
    @GetMapping("/{userId}")
    public List<Expense> getExpenses(@PathVariable Long userId) {
        return expenseService.getExpensesByUser(userId);
    }
    @GetMapping("/me")
    public List<Expense> getMyExpenses() {

        return expenseService.getMyExpenses();
    }
    @PutMapping("/{expenseId}")
    public Expense updateExpense(
            @PathVariable Long expenseId,
            @RequestBody Expense expense) {

        return expenseService.updateExpense(expenseId, expense);
    }
    @DeleteMapping("/{expenseId}")
    public String deleteExpense(@PathVariable Long expenseId) {

        expenseService.deleteExpense(expenseId);

        return "Expense deleted successfully";
    }
}