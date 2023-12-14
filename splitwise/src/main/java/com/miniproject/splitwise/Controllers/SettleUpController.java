package com.miniproject.splitwise.Controllers;

import com.miniproject.splitwise.DTOs.*;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpGroupRequestDTO;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpGroupResponseDTO;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpUserRequestDTO;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpUserResponseDTO;
import com.miniproject.splitwise.Exceptions.GroupNotFoundException;
import com.miniproject.splitwise.Exceptions.UserNotFoundException;
import com.miniproject.splitwise.Services.SettleUpService;
import com.miniproject.splitwise.Services.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SettleUpController {

    private SettleUpService settleUpService;

    @Autowired
    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    public SettleUpUserResponseDTO settleUpUser (SettleUpUserRequestDTO userRequestDTO) {
        SettleUpUserResponseDTO userResponseDTO = new SettleUpUserResponseDTO();
        List<Transaction> transactionList;

        try {
            transactionList = settleUpService.settleUpUser(userRequestDTO.getUserId());
            userResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (UserNotFoundException ex) {
            userResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            userResponseDTO.setFailureReason(ex.getMessage());
            return userResponseDTO;
        }
        userResponseDTO.setTransactions(transactionList);

        return userResponseDTO;
    }

    public SettleUpGroupResponseDTO settleUpGroup (SettleUpGroupRequestDTO groupRequestDTO) {
        SettleUpGroupResponseDTO groupResponseDTO = new SettleUpGroupResponseDTO();
        List<Transaction> transactionList;

        try {
            transactionList = settleUpService.settleUpGroup(groupRequestDTO.getGroupId());
            groupResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (GroupNotFoundException ex) {
            groupResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            groupResponseDTO.setFailureReason(ex.getMessage());
            return groupResponseDTO;
        }
        groupResponseDTO.setTransactions(transactionList);

        return groupResponseDTO;
    }
}
