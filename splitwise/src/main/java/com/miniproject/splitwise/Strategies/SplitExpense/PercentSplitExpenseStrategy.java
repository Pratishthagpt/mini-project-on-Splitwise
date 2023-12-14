package com.miniproject.splitwise.Strategies.SplitExpense;

import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.ExpenseUserType;
import com.miniproject.splitwise.Models.User;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PercentSplitExpenseStrategy implements SplitExpenseStrategy{
    @Override
    public ExpenseUser getExpenseUser(User user, int userIndx,
                                      List<Integer> split, int totalAmount) {
        ExpenseUser expenseUser = new ExpenseUser();
        expenseUser.setUser(user);
        int sumSplitPercent = 100;
        int userAmount = (totalAmount * split.get(userIndx)) / sumSplitPercent;
        expenseUser.setAmount(userAmount);
        if (userIndx == 0) {
            expenseUser.setExpenseUserType(ExpenseUserType.WHO_PAID);
        }
        else {
            expenseUser.setExpenseUserType(ExpenseUserType.WHO_HAD_TO_PAY);
        }
        return expenseUser;
    }
}
