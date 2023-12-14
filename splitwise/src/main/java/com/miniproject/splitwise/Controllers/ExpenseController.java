package com.miniproject.splitwise.Controllers;

import com.miniproject.splitwise.DTOs.*;
import com.miniproject.splitwise.DTOs.ExpenseDTO.GroupExpenseRequestDTO;
import com.miniproject.splitwise.DTOs.ExpenseDTO.GroupExpenseResponseDTO;
import com.miniproject.splitwise.DTOs.ExpenseDTO.UserExpenseRequestDTO;
import com.miniproject.splitwise.DTOs.ExpenseDTO.UserExpenseResponseDTO;
import com.miniproject.splitwise.Exceptions.GroupNotFoundException;
import com.miniproject.splitwise.Exceptions.InvalidSplitFoundException;
import com.miniproject.splitwise.Exceptions.UserNotFoundException;
import com.miniproject.splitwise.Models.Expense;
import com.miniproject.splitwise.Services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExpenseController {

    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public UserExpenseResponseDTO addUserExpense (UserExpenseRequestDTO expenseRequestDTO) {
        UserExpenseResponseDTO userExpenseResponseDTO = new UserExpenseResponseDTO();

        Expense expense;
        try {
            expense = expenseService.addUserExpense(
                    expenseRequestDTO.getUserId(),
                    expenseRequestDTO.getInvolvedUsersId(),
                    expenseRequestDTO.getTotalAmount(),
                    expenseRequestDTO.getSplitType(),
                    expenseRequestDTO.getSplit(),
                    expenseRequestDTO.getDescription());
            userExpenseResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            userExpenseResponseDTO.setExpense(expense);
        }catch (UserNotFoundException | InvalidSplitFoundException e) {
            userExpenseResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            userExpenseResponseDTO.setFailureReason(e.getMessage());
        }
        return userExpenseResponseDTO;
    }

    public GroupExpenseResponseDTO addGroupExpense (GroupExpenseRequestDTO groupExpenseRequestDTO) {
        GroupExpenseResponseDTO groupExpenseResponseDTO = new GroupExpenseResponseDTO();

        Expense expense;
        try {
            expense = expenseService.addGroupExpense(
                    groupExpenseRequestDTO.getUserId(),
                    groupExpenseRequestDTO.getGroupId(),
                    groupExpenseRequestDTO.getTotalAmount(),
                    groupExpenseRequestDTO.getSplitType(),
                    groupExpenseRequestDTO.getSplit(),
                    groupExpenseRequestDTO.getDescription());
            groupExpenseResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            groupExpenseResponseDTO.setExpense(expense);
        }catch (UserNotFoundException | GroupNotFoundException | InvalidSplitFoundException e) {
            groupExpenseResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            groupExpenseResponseDTO.setFailureReason(e.getMessage());
        }

        return groupExpenseResponseDTO;
    }

}
