package com.miniproject.splitwise.DTOs.GroupDTO;

import com.miniproject.splitwise.DTOs.ResponseStatus;
import com.miniproject.splitwise.Models.Group;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroupResponseDTO {
    private Group group;
    private ResponseStatus responseStatus;
    private String failureReason;
}
