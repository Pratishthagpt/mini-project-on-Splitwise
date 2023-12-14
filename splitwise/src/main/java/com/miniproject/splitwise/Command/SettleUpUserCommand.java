package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.SettleUpController;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpUserRequestDTO;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettleUpUserCommand implements Command{
//    Input format - u1 SettleUp

    @Autowired
    private SettleUpController settleUpController;

    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        Long userId = Long.valueOf(words.get(0));

        SettleUpUserRequestDTO userRequestDTO = new SettleUpUserRequestDTO();
        userRequestDTO.setUserId(userId);

        SettleUpUserResponseDTO responseDTO =
                settleUpController.settleUpUser(userRequestDTO);
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 2 && words.get(1).equalsIgnoreCase(CommandKeywords.settleUp);
    }
}
