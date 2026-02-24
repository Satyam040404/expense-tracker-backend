package com.satyam.expensetracker.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
@Entity
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double amount;
    private String category;
    private LocalDate date;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;
}
