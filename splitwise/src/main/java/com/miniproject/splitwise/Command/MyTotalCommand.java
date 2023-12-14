package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.SettleUpController;
import com.miniproject.splitwise.Controllers.UserAuthController;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpUserRequestDTO;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpUserResponseDTO;
import com.miniproject.splitwise.DTOs.UserDTO.UserAuthResponseDTO;
import com.miniproject.splitwise.DTOs.UserDTO.UserLoginRequestDTO;
import com.miniproject.splitwise.Models.User;
import com.miniproject.splitwise.Repositories.UserRepository;
import com.miniproject.splitwise.Services.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyTotalCommand implements Command{
//    Input format - u1 MyTotal
//    u1 is asking to see the total amount they owe/receive after everything is settled.
//    For getting the total amount of user (owes/owed by), we need to settle up as per the requirement

    @Autowired
    private SettleUpController settleUpController;
    private UserRepository userRepository;


    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        Long userId = Long.valueOf(words.get(0));

        User user = userRepository.findById(userId).get();

        SettleUpUserRequestDTO userRequestDTO = new SettleUpUserRequestDTO();
        userRequestDTO.setUserId(userId);

        SettleUpUserResponseDTO responseDTO =
                settleUpController.settleUpUser(userRequestDTO);

        List<Transaction> transactions = responseDTO.getTransactions();
        System.out.println("Here is the list of transactions: ");
        int totalAmount = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getFrom().getId().equals(userId)) {
                totalAmount = totalAmount - transaction.getAmount();
            }
            else if (transaction.getTo().getId().equals(userId)) {
                totalAmount = totalAmount + transaction.getAmount();
            }
        }
        if (totalAmount > 0) {
            System.out.println(user.getName() + " is owed by total " + totalAmount);
        }
        else if (totalAmount < 0) {
            System.out.println(user.getName() + " owes " + Math.abs(totalAmount) + " in total.");
        }
        else {
            System.out.println(user.getName() + " is all settled up.");
        }
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return (words.size() == 2 && words.get(1).equals(CommandKeywords.userTotal));
    }
}
