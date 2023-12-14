package com.miniproject.splitwise.DTOs.UserDTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDTO {
    private String email;
    private String password;
}
