package com.miniproject.splitwise.Models.ExpenseSplit;

import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PercentExpenseSplit extends ExpenseUser {
    private int percent;
    public PercentExpenseSplit(User user, int percent) {
        super(user);
        this.percent = percent;
    }
}
