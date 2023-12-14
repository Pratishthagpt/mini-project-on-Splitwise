package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.ExpenseController;
import com.miniproject.splitwise.DTOs.ExpenseDTO.UserExpenseRequestDTO;
import com.miniproject.splitwise.DTOs.ExpenseDTO.UserExpenseResponseDTO;
import com.miniproject.splitwise.Models.User;
import com.miniproject.splitwise.Repositories.GroupRepository;
import com.miniproject.splitwise.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddUserExpenseCommand implements Command{
//  Input format - u1 Expense u2 u3 u4 1000 Ratio 1 2 3 4 Desc Goa trip


    @Autowired
    private GroupRepository groupRepository;
    private ExpenseController expenseController;
    private UserRepository userRepository;


    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        Long userId = Long.valueOf(words.get(0));
        List<Long> userIdInvolvedInExpense = new ArrayList<>();
        int i = 2;
        while (userRepository.findById(Long.valueOf(words.get(i))).isPresent()) {
            userIdInvolvedInExpense.add(Long.valueOf(words.get(i)));
            i++;
        }

        int totalAmount = Integer.parseInt(words.get(i));
        i++;
        String splitType = words.get(i);
        i++;

        List<Integer> split = new ArrayList<>();
        if (splitType.equalsIgnoreCase("EQUAL")) {
//            total users = userIdInvolvedInExpense.size() + 1
            for(int j = 0; j < userIdInvolvedInExpense.size() + 1; j++) {
                split.add(1);
            }
        }
        else {
            while (!words.get(i).equalsIgnoreCase("DESC")) {
                split.add(Integer.valueOf(words.get(i)));
                i++;
            }
        }
        i++;
        StringBuilder description = new StringBuilder();
        for(int j = i; j < words.size() - 1; j++) {
            description.append(words.get(j));
            description.append(" ");
        }
        description.append(words.get(words.size() - 1));

        UserExpenseRequestDTO userExpenseRequestDTO = new UserExpenseRequestDTO();
        userExpenseRequestDTO.setUserId(userId);
        userExpenseRequestDTO.setInvolvedUsersId(userIdInvolvedInExpense);
        userExpenseRequestDTO.setTotalAmount(totalAmount);
        userExpenseRequestDTO.setSplit(split);
        userExpenseRequestDTO.setSplitType(splitType);
        userExpenseRequestDTO.setDescription(description.toString());

        UserExpenseResponseDTO responseDTO =
                expenseController.addUserExpense(userExpenseRequestDTO);
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return (words.size() >= 7 && words.get(1).equals(CommandKeywords.expense)
                && groupRepository.findById(Long.valueOf(words.get(2))).isEmpty());
    }
}
