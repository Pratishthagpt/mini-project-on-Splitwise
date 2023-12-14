package com.miniproject.splitwise.Repositories;

import com.miniproject.splitwise.Models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Expense save (Expense expense);
}
