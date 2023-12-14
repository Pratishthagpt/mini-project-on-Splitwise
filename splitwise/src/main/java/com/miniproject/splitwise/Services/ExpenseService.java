package com.miniproject.splitwise.Services;

import com.miniproject.splitwise.Exceptions.GroupNotFoundException;
import com.miniproject.splitwise.Exceptions.InvalidSplitFoundException;
import com.miniproject.splitwise.Exceptions.UserNotFoundException;
import com.miniproject.splitwise.Models.*;
import com.miniproject.splitwise.Repositories.ExpenseUserRepository;
import com.miniproject.splitwise.Repositories.GroupRepository;
import com.miniproject.splitwise.Repositories.UserRepository;
import com.miniproject.splitwise.Strategies.SplitExpense.SplitExpenseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private UserRepository userRepository;
    private SplitExpenseFactory splitExpenseFactory;
    private ExpenseUserRepository expenseUserRepository;
    private GroupRepository groupRepository;

    @Autowired
    public ExpenseService(UserRepository userRepository,
                          SplitExpenseFactory splitExpenseFactory,
                          ExpenseUserRepository expenseUserRepository,
                          GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.splitExpenseFactory = splitExpenseFactory;
        this.expenseUserRepository = expenseUserRepository;
        this.groupRepository = groupRepository;
    }

    public Expense addUserExpense(Long userId, List<Long> involvedUsersId,
                                  int totalAmount, String splitType, List<Integer> split, String description){
        /**
         * 1. Get user from userId.
         * 2. Get expenseSplitType from splitType
         * 2. Get List of User from involved userId
         * 3. Get List of ExpenseUser from List of user and expenseSplitType
         * 4. Set all the values into new Expense created initially.
         * 5. Save the expense and return.
         * */

//        1. Get the created by user
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Sorry!! No user is there with the given userId. " +
                    "Please enter the valid userId.");
        }
        User user = userOptional.get();

//        2. Get the expense split type
        ExpenseSplitType expenseSplitType = getExpenseSplitType(splitType);
//        3. Check the validation of split
        if (!splitValidation(expenseSplitType, split, totalAmount)) {
            throw new InvalidSplitFoundException("Please check either the total percent " +
                    "is not equal to 100, or the exact amount total does not matches to total " +
                    "amount of expense.");
        }

//        4. Get the list of Expense user
        List<ExpenseUser> expenseUserList = new ArrayList<>();
//        For expense created by user (user at index 0)
        ExpenseUser createdByExpenseUser = splitExpenseFactory.getExpenseUserBasedOnSplitType(expenseSplitType,
                user, 0, split, totalAmount);
        expenseUserRepository.save(createdByExpenseUser);
        expenseUserList.add(createdByExpenseUser);

        for (int i = 0; i < involvedUsersId.size(); i++) {
            Long id = involvedUsersId.get(i);
            Optional<User> userInvolvedOptional = userRepository.findById(userId);
            if (userInvolvedOptional.isEmpty()) {
                throw new UserNotFoundException("Sorry!! No user is there with the given userId. " +
                        "Please enter the valid userId.");
            }
            User userInvolved = userInvolvedOptional.get();

            ExpenseUser nextExpenseUser = splitExpenseFactory.getExpenseUserBasedOnSplitType(
                    expenseSplitType, userInvolved, i+1, split, totalAmount);
            expenseUserRepository.save(nextExpenseUser);
            expenseUserList.add(nextExpenseUser);
        }

        Expense expenseCreated = new Expense();
        expenseCreated.setAmount(totalAmount);
        expenseCreated.setCreatedBy(user);
        expenseCreated.setExpenseType(ExpenseType.NORMAL);
        expenseCreated.setExpenseUsers(expenseUserList);
        expenseCreated.setCreatedAt(new Date());
        expenseCreated.setExpenseSplitType(expenseSplitType);
        expenseCreated.setDescription(description);

        return expenseCreated;
    }
    public Expense addGroupExpense(Long userId, Long groupId,
                                   int totalAmount, String splitType, List<Integer> split, String description){
        /**
         * 1. Get user from userId and group from groupId.
         * 2. Get expenseSplitType from splitType
         * 2. Get List of User from involved userId
         * 3. Get List of ExpenseUser from List of user and expenseSplitType
         * 4. Set all the values into new Expense created initially.
         * 5. Save the expense and return.
         * */

//        1. Get the created by user and group Id
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Sorry!! No user is there with the given userId. " +
                    "Please enter the valid userId.");
        }
        User user = userOptional.get();

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty()) {
            throw new GroupNotFoundException("No group is present with the input groupId.");
        }
        Group group = groupOptional.get();

        List<User> groupUsers = group.getUsers();


//        2. Get the expense split type
        ExpenseSplitType expenseSplitType = getExpenseSplitType(splitType);
//        3. Check the validation of split
        if (!splitValidation(expenseSplitType, split, totalAmount)) {
            throw new InvalidSplitFoundException("Please check either the total percent " +
                    "is not equal to 100, or the exact amount total does not matches to total " +
                    "amount of expense.");
        }

//        4. Get the list of Expense user
        List<ExpenseUser> expenseUserList = new ArrayList<>();

        for (int i = 0; i < groupUsers.size(); i++) {
            User userInvolved = groupUsers.get(i);

            ExpenseUser nextExpenseUser = splitExpenseFactory.getExpenseUserBasedOnSplitType(
                    expenseSplitType, userInvolved, i, split, totalAmount);
            expenseUserRepository.save(nextExpenseUser);
            expenseUserList.add(nextExpenseUser);
        }

        Expense expenseCreated = new Expense();
        expenseCreated.setAmount(totalAmount);
        expenseCreated.setCreatedBy(user);
        expenseCreated.setExpenseType(ExpenseType.NORMAL);
        expenseCreated.setGroup(group);
        expenseCreated.setExpenseUsers(expenseUserList);
        expenseCreated.setCreatedAt(new Date());
        expenseCreated.setExpenseSplitType(expenseSplitType);
        expenseCreated.setDescription(description);

        return expenseCreated;
    }

    private boolean splitValidation(ExpenseSplitType expenseSplitType, List<Integer> spilt,
                                    int totalAmount) {
        if (expenseSplitType.equals(ExpenseSplitType.EQUAL) ||
                expenseSplitType.equals(ExpenseSplitType.RATIO)) {
            return true;
        }
        else if (expenseSplitType.equals(ExpenseSplitType.EXACT)) {
            int sumSplitAmount = 0;
            for (Integer amountSplit : spilt) {
                sumSplitAmount += amountSplit;
            }
            if (totalAmount == sumSplitAmount) return true;
        }
        else if (expenseSplitType.equals(ExpenseSplitType.PERCENT)) {
            int totalPercent = 100;
            int sumsplitPercent = 0;
            for (Integer percent : spilt) {
                sumsplitPercent += percent;
            }
            if (totalAmount == sumsplitPercent) return true;
        }
        return false;
    }

    private ExpenseSplitType getExpenseSplitType (String splitType) {
        ExpenseSplitType expenseSplitType = ExpenseSplitType.EQUAL;
        if (splitType.equalsIgnoreCase("EXACT")) {
            expenseSplitType = ExpenseSplitType.EXACT;
        }
        else if (splitType.equalsIgnoreCase("RATIO")) {
            expenseSplitType = ExpenseSplitType.RATIO;
        }
        else if (splitType.equalsIgnoreCase("EQUAL")) {
            expenseSplitType = ExpenseSplitType.EQUAL;
        }
        else if (splitType.equalsIgnoreCase("PERCENT")) {
            expenseSplitType = ExpenseSplitType.PERCENT;
        }
        return expenseSplitType;
    }
}
