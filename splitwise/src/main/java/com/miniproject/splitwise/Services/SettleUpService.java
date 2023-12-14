package com.miniproject.splitwise.Services;

import com.miniproject.splitwise.Exceptions.GroupNotFoundException;
import com.miniproject.splitwise.Exceptions.UserNotFoundException;
import com.miniproject.splitwise.Models.Expense;
import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.Group;
import com.miniproject.splitwise.Models.User;
import com.miniproject.splitwise.Repositories.ExpenseUserRepository;
import com.miniproject.splitwise.Repositories.GroupRepository;
import com.miniproject.splitwise.Repositories.UserRepository;
import com.miniproject.splitwise.Strategies.SettleUp.SettleUpStrategy;
import com.miniproject.splitwise.Strategies.SettleUp.TwoHeapSettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SettleUpService {
    private UserRepository userRepository;
    private ExpenseUserRepository expenseUserRepository;
    private SettleUpStrategy settleUpStrategy;
    private GroupRepository groupRepository;

    @Autowired
    public SettleUpService(UserRepository userRepository,
                           ExpenseUserRepository expenseUserRepository,
                           SettleUpStrategy settleUpStrategy,
                           GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.groupRepository = groupRepository;
    }

    public List<Transaction> settleUpUser (Long userId) {
        /**
         * 1. Get user from userId.
         * 2. Get all the List of expenses for which user is involved
         * 3. Get all the people involved and the amount owes/ owed by people.
         * 4. Calculate the list of transaction using the strategy of min-max heap
         * 5. Filter the list of transaction and get only those transactions for which user is part
         * of either as (from) or (to)
         *    (Here we won't save the list of transactions because the transactions are calculated
         *    dynamically whenever the user clicks on settle up button)
         * 6. Return the List of Transactions.
         *
         */
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Sorry!! No user is there with the given userId. Please" +
                    "enter the valid userId.");
        }
        User user = userOptional.get();

        List<ExpenseUser> expenseUserList = expenseUserRepository.findAllByUser(user);
        List<Expense> expenses = new ArrayList<>();

        for (ExpenseUser expenseUser : expenseUserList) {
            expenses.add(expenseUser.getExpense());
        }

        settleUpStrategy = new TwoHeapSettleUpStrategy(); // can be modified if more strategies are added later on

        List<Transaction> transactions = settleUpStrategy.settleUp(expenses);
        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getFrom().equals(user) || transaction.getTo().equals(user)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    public List<Transaction> settleUpGroup (Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty()) {
            throw new GroupNotFoundException("No group is present with the input groupId.");
        }
        Group group = groupOptional.get();

        List<Expense> groupExpenses = group.getExpenses();
        settleUpStrategy = new TwoHeapSettleUpStrategy(); // can be modified if more strategies are added later on

        List<Transaction> transactions = settleUpStrategy.settleUp(groupExpenses);

        return transactions;
    }
}
