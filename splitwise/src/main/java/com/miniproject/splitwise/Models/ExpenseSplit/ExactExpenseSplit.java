package com.miniproject.splitwise.Models.ExpenseSplit;

import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.User;

public class ExactExpenseSplit extends ExpenseUser {
    private int amount;
    public ExactExpenseSplit(User user, int amount) {
        super(user);
        this.amount = amount;
    }
}
