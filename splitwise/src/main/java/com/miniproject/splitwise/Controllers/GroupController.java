package com.miniproject.splitwise.Controllers;


import com.miniproject.splitwise.DTOs.GroupDTO.AddGroupRequestDTO;
import com.miniproject.splitwise.DTOs.GroupDTO.AddGroupResponseDTO;
import com.miniproject.splitwise.DTOs.GroupDTO.ShowGroupsByUserRequestDTO;
import com.miniproject.splitwise.DTOs.GroupDTO.ShowGroupsByUserResponseDTO;
import com.miniproject.splitwise.DTOs.ResponseStatus;
import com.miniproject.splitwise.Exceptions.UserNotFoundException;
import com.miniproject.splitwise.Models.Group;
import com.miniproject.splitwise.Services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupController {
    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    public AddGroupResponseDTO addGroup (AddGroupRequestDTO addGroupRequestDTO) {
        AddGroupResponseDTO groupResponseDTO = new AddGroupResponseDTO();
        Group newGroup;

        try {
            newGroup = groupService.createGroup(addGroupRequestDTO.getName(),
                    addGroupRequestDTO.getDescription(), addGroupRequestDTO.getAdminId(),
                    addGroupRequestDTO.getGroupMembersId());
            groupResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            groupResponseDTO.setGroup(newGroup);
        }catch (UserNotFoundException e) {
            groupResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            groupResponseDTO.setFailureReason(e.getMessage());
        }
        return groupResponseDTO;
    }
    public ShowGroupsByUserResponseDTO showGroupsByUser(ShowGroupsByUserRequestDTO showGroupsByUserRequestDTO) {
        ShowGroupsByUserResponseDTO responseDTO = new ShowGroupsByUserResponseDTO();
        List<Group> userInvolvedGroups;

        try {
            userInvolvedGroups = groupService.showGroupsByUser(
                    showGroupsByUserRequestDTO.getUserId());
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            responseDTO.setGroups(userInvolvedGroups);
        }catch (UserNotFoundException e) {
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
            responseDTO.setFailureReason(e.getMessage());
        }
        return responseDTO;
    }
}
