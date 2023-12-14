package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.GroupController;
import com.miniproject.splitwise.DTOs.GroupDTO.AddGroupRequestDTO;
import com.miniproject.splitwise.DTOs.GroupDTO.AddGroupResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddGroupCommand implements Command{

    //    Input format - u1 AddGroup Roommates GroceryExpenses AddMember g1 u2
    @Autowired
    private GroupController groupController;



    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        Long adminId = Long.valueOf(words.get(0));
        String groupName = words.get(2);
        String description = words.get(3);
        Long groupId = Long.valueOf(words.get(5));
        List<Long> groupMembersId = new ArrayList<>();

        for (int i = 6; i < words.size(); i++) {
            groupMembersId.add(Long.valueOf(words.get(i)));
        }

        AddGroupRequestDTO addGroupRequestDTO = new AddGroupRequestDTO();
        addGroupRequestDTO.setName(groupName);
        addGroupRequestDTO.setDescription(description);
        addGroupRequestDTO.setAdminId(adminId);
        addGroupRequestDTO.setGroupMembersId(groupMembersId);

        AddGroupResponseDTO addGroupResponseDTO = groupController.addGroup(addGroupRequestDTO);
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return (words.size() >= 7 && words.get(1).equals(CommandKeywords.addGroup) &&
                words.get(4).equals(CommandKeywords.addMember));
    }
}
