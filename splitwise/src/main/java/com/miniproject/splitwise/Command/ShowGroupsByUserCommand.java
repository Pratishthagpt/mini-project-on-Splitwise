package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.GroupController;
import com.miniproject.splitwise.DTOs.GroupDTO.ShowGroupsByUserRequestDTO;
import com.miniproject.splitwise.DTOs.GroupDTO.ShowGroupsByUserResponseDTO;
import com.miniproject.splitwise.Models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowGroupsByUserCommand implements Command{
//    Input format - u1 Groups

    @Autowired
    private GroupController groupController;


    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        Long userId = Long.valueOf(words.get(0));

        ShowGroupsByUserRequestDTO showGroupsByUserRequestDTO =
                new ShowGroupsByUserRequestDTO();
        showGroupsByUserRequestDTO.setUserId(userId);

        ShowGroupsByUserResponseDTO responseDTO = groupController.showGroupsByUser(
                showGroupsByUserRequestDTO);
        List<Group> userGroups = responseDTO.getGroups();
        if (userGroups.size() == 0) {
            System.out.println("User is not the member of any group.");
        }
        else {
            System.out.println("Here is the list of groups in which user is member: ");
            for (Group group : userGroups) {
                System.out.println(group.getName());
            }
        }
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 2 && words.get(1).equalsIgnoreCase(CommandKeywords.showGroups);

    }
}
