package com.miniproject.splitwise.Strategies.SettleUp;

import com.miniproject.splitwise.Models.Expense;
import com.miniproject.splitwise.Services.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SettleUpStrategy {
    public List<Transaction> settleUp (List<Expense> expenses);
}
