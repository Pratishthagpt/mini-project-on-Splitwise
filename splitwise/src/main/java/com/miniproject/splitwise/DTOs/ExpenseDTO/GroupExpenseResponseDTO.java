package com.miniproject.splitwise.DTOs.ExpenseDTO;

import com.miniproject.splitwise.DTOs.ResponseStatus;
import com.miniproject.splitwise.Models.Expense;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GroupExpenseResponseDTO {
    private Expense expense;
    private ResponseStatus responseStatus;
    private String failureReason;
}
