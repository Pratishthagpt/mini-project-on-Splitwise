package com.miniproject.splitwise.Strategies.SplitExpense;

import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SplitExpenseStrategy {
    public ExpenseUser getExpenseUser(User user, int userIndx, List<Integer> split, int totalAmount);
}
