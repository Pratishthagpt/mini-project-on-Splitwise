package com.miniproject.splitwise.Strategies.SettleUp;

import com.miniproject.splitwise.Models.Expense;
import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.ExpenseUserType;
import com.miniproject.splitwise.Models.User;
import com.miniproject.splitwise.Services.Transaction;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TwoHeapSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        /**
         * - For settling up the expenses, we will try to maintain two heaps (one is for people who
         *   will get the money and the other who has to pay).
         * 1. MaxHeap for who will get money (keeping as +ve expense)
         * 2. MinHeap for who has to pay money (keeping as -ve expense)
         * - Now, till both the heaps got empty, we will take out the max amount expense from
         *   both heaps (max (+ve) from max heap and max (-ve) from min heap)
         * - We will settle up one user (with min absolute money out of two) and then put the
         *   other one back (with the difference left) to heap.
         * - In this manner, in each iteration we are settling up some user and store the transactions
         *   accordingly in list.
         * */
        PriorityQueue<Pair<User, Integer>> whoWillGet = new PriorityQueue<>(Collections.reverseOrder()); // maxHeap
        PriorityQueue<Pair<User, Integer>> whoHasToPay = new PriorityQueue<>(); // minHeap

        // 1. Separating the expenses in between whoWillGet and whoHasToPay
        for (Expense expense : expenses) {
            List<ExpenseUser> expenseUserList = expense.getExpenseUsers();
            for (ExpenseUser expenseUser : expenseUserList) {
                User user = expenseUser.getUser();
                int amount = expenseUser.getAmount();
                if (expenseUser.getExpenseUserType().equals(ExpenseUserType.WHO_PAID)) {
                    // As in expense, th person who paid money before, now will become the person
                    // who will receive money (who will get)
                    whoWillGet.add(Pair.of(user, amount));
                }
                else if (expenseUser.getExpenseUserType().equals(ExpenseUserType.WHO_HAD_TO_PAY)) {
                   // As in expense, th person who had to pay money did not pay before
                   // , now will become the person who will pay money (who has to pay)
                    whoHasToPay.add(Pair.of(user, amount));
                }
            }
        }

//        2. Settling up the two heaps
        List<Transaction> transactions = new ArrayList<>();
        while (!whoWillGet.isEmpty() && whoHasToPay.isEmpty()) {
            Pair<User, Integer> getPair = whoWillGet.poll();
            User userGettingMoney = getPair.getFirst();
            int positiveAmount = getPair.getSecond();

            Pair<User, Integer> payPair = whoHasToPay.poll();
            User userPayingMoney = payPair.getFirst();
            int negativeAmount = payPair.getSecond();

            Transaction currTransaction = new Transaction();
            if (positiveAmount < negativeAmount) {
//      person who will get most money will get resolve and his positive amount becomes zero
                negativeAmount = negativeAmount - positiveAmount;
                currTransaction.setAmount(positiveAmount);
                currTransaction.setFrom(userPayingMoney);
                currTransaction.setTo(userGettingMoney);

                whoHasToPay.add(Pair.of(userPayingMoney, negativeAmount));
            }
            else if (positiveAmount > negativeAmount) {
//      person who will pay most money will get resolve and his negative amount becomes zero
                positiveAmount = positiveAmount - negativeAmount;
                currTransaction.setAmount(negativeAmount);
                currTransaction.setFrom(userPayingMoney);
                currTransaction.setTo(userGettingMoney);

                whoWillGet.add(Pair.of(userGettingMoney, positiveAmount));
            }
            else {
//                Both persons expense will get resolve and nothing will be added to heaps
//                positiveAmount = 0, negativeAmount = 0
                currTransaction.setAmount(positiveAmount);
                currTransaction.setFrom(userPayingMoney);
                currTransaction.setTo(userGettingMoney);
            }
            transactions.add(currTransaction);
        }
        return transactions;
    }
}
