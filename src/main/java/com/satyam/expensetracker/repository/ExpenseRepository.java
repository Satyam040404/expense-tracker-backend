package com.satyam.expensetracker.repository;

import com.satyam.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    @Query("""
       SELECT e.category, SUM(e.amount)
       FROM Expense e
       WHERE e.user.id = :userId
       GROUP BY e.category
       """)
    List<Object[]> getExpenseSummaryByUser(Long userId);
    List<Expense> findByUserIdAndCategory(
            Long userId,
            String category
    );
    List<Expense> findByUserIdAndTitleContainingIgnoreCase(
            Long userId,
            String keyword
    );
}