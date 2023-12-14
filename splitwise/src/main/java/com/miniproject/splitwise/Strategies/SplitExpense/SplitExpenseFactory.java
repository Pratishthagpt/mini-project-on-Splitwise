package com.miniproject.splitwise.Strategies.SplitExpense;

import com.miniproject.splitwise.Models.ExpenseSplitType;
import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SplitExpenseFactory {
    public ExpenseUser getExpenseUserBasedOnSplitType (ExpenseSplitType expenseSplitType,
                         User user, int userIndx, List<Integer> split, int totalAmount) {
        if (expenseSplitType.equals(ExpenseSplitType.EQUAL)) {
            SplitExpenseStrategy splitExpenseStrategy = new EqualSplitExpenseStrategy();
            return splitExpenseStrategy.getExpenseUser(user, userIndx, split, totalAmount);
        }
        else if (expenseSplitType.equals(ExpenseSplitType.EXACT)) {
            SplitExpenseStrategy splitExpenseStrategy = new ExactSplitExpenseStrategy();
            return splitExpenseStrategy.getExpenseUser(user, userIndx, split, totalAmount);
        }
        else if (expenseSplitType.equals(ExpenseSplitType.RATIO)) {
            SplitExpenseStrategy splitExpenseStrategy = new RatioSplitExpenseStrategy();
            return splitExpenseStrategy.getExpenseUser(user, userIndx, split, totalAmount);
        }
        else if (expenseSplitType.equals(ExpenseSplitType.PERCENT)) {
            SplitExpenseStrategy splitExpenseStrategy = new PercentSplitExpenseStrategy();
            return splitExpenseStrategy.getExpenseUser(user, userIndx, split, totalAmount);
        }
        else return null;
    }
}
