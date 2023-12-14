package com.miniproject.splitwise.DTOs.UserDTO;

import com.miniproject.splitwise.DTOs.ResponseStatus;
import com.miniproject.splitwise.Models.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAuthResponseDTO {
    private User user;
    private ResponseStatus responseStatus;
    private String failureReason;
}
