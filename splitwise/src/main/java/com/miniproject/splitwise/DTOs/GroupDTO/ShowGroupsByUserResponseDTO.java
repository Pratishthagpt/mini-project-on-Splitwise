package com.miniproject.splitwise.DTOs.GroupDTO;

import com.miniproject.splitwise.DTOs.ResponseStatus;
import com.miniproject.splitwise.Models.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowGroupsByUserResponseDTO {
    private List<Group> groups;
    private ResponseStatus responseStatus;
    private String failureReason;
}
