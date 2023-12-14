package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.SettleUpController;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpGroupRequestDTO;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpGroupResponseDTO;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpUserRequestDTO;
import com.miniproject.splitwise.DTOs.SettleUpDTO.SettleUpUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettleUpGroupCommand implements Command{
//    Input format - u1 SettleUp g1
    @Autowired

    private SettleUpController settleUpController;


    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        Long groupId = Long.valueOf(words.get(2));

        SettleUpGroupRequestDTO groupRequestDTO = new SettleUpGroupRequestDTO();
        groupRequestDTO.setGroupId(groupId);

        SettleUpGroupResponseDTO responseDTO =
                settleUpController.settleUpGroup(groupRequestDTO);
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 3 && words.get(1).equalsIgnoreCase(CommandKeywords.settleUp);
    }
}
