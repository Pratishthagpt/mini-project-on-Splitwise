package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.ExpenseController;
import com.miniproject.splitwise.DTOs.ExpenseDTO.GroupExpenseRequestDTO;
import com.miniproject.splitwise.DTOs.ExpenseDTO.GroupExpenseResponseDTO;
import com.miniproject.splitwise.Models.User;
import com.miniproject.splitwise.Repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddGroupExpenseCommand implements Command{
//    Input format - u1 Expense g1 1000 Equal Desc Wifi Bill

    @Autowired
    private GroupRepository groupRepository;
    private ExpenseController expenseController;


    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        Long userId = Long.valueOf(words.get(0));
        Long groupId = Long.valueOf(words.get(2));
        int totalAmount = Integer.parseInt(words.get(3));
        String splitType = words.get(4);
        int i = 5;  // indx after the split type

        List<Integer> split = new ArrayList<>();
        if (splitType.equalsIgnoreCase("EQUAL")) {
            List<User> members = groupRepository.findById(groupId).get().getUsers();
            for(int j = 0; j < members.size(); j++) {
                split.add(1);
            }
        }
        else {
            i = 5;
            while (!words.get(i).equalsIgnoreCase("DESC")) {
                split.add(Integer.valueOf(words.get(i)));
                i++;
            }
        }

        StringBuilder description = new StringBuilder();
        for(int j = i+1; j < words.size() - 1; j++) {
            description.append(words.get(j));
            description.append(" ");
        }
        description.append(words.get(words.size() - 1));

        GroupExpenseRequestDTO groupExpenseRequestDTO = new GroupExpenseRequestDTO();
        groupExpenseRequestDTO.setUserId(userId);
        groupExpenseRequestDTO.setGroupId(groupId);
        groupExpenseRequestDTO.setTotalAmount(totalAmount);
        groupExpenseRequestDTO.setSplitType(splitType);
        groupExpenseRequestDTO.setSplit(split);
        groupExpenseRequestDTO.setDescription(description.toString());

        GroupExpenseResponseDTO groupExpenseResponseDTO =
                expenseController.addGroupExpense(groupExpenseRequestDTO);

    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return (words.size() >= 7 && words.get(1).equals(CommandKeywords.expense)
        && groupRepository.findById(Long.valueOf(words.get(2))).isPresent());
    }
}
