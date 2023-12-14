package com.miniproject.splitwise.DTOs.SettleUpDTO;

import com.miniproject.splitwise.DTOs.ResponseStatus;
import com.miniproject.splitwise.Services.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleUpUserResponseDTO {
    private List<Transaction> transactions;
    private ResponseStatus responseStatus;
    private String failureReason;
}
