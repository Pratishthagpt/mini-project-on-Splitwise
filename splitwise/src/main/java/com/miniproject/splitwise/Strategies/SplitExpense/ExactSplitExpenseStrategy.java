package com.miniproject.splitwise.Strategies.SplitExpense;

import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.ExpenseUserType;
import com.miniproject.splitwise.Models.User;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ExactSplitExpenseStrategy implements SplitExpenseStrategy{
    @Override
    public ExpenseUser getExpenseUser(User user, int userIndx,
                                      List<Integer> split, int totalAmount) {
        ExpenseUser expenseUser = new ExpenseUser();
        expenseUser.setUser(user);
        expenseUser.setAmount(split.get(userIndx));
        if (userIndx == 0) {
            expenseUser.setExpenseUserType(ExpenseUserType.WHO_PAID);
        }
        else {
            expenseUser.setExpenseUserType(ExpenseUserType.WHO_HAD_TO_PAY);
        }

        return expenseUser;
    }
}
