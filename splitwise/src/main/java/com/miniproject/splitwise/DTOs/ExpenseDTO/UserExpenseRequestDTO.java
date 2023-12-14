package com.miniproject.splitwise.DTOs.ExpenseDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserExpenseRequestDTO {
    private Long userId;
    private List<Long> involvedUsersId;
    private int totalAmount;
    private String splitType;
    private List<Integer> split;
    private String description;

}
