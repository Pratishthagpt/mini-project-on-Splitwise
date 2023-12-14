package com.miniproject.splitwise.DTOs.GroupDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AddGroupRequestDTO {
    private String name;
    private String description;
    private Long adminId;
    private List<Long> groupMembersId;
}
