package com.miniproject.splitwise.Models.ExpenseSplit;


import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatioExpenseSplit extends ExpenseUser {
    private int ratio;

    public RatioExpenseSplit(User user, int ratio) {
        super(user);
        this.ratio = ratio;
    }
}
