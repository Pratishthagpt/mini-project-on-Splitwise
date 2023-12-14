package com.miniproject.splitwise.Controllers;

import com.miniproject.splitwise.DTOs.*;
import com.miniproject.splitwise.DTOs.UserDTO.UserLoginRequestDTO;
import com.miniproject.splitwise.DTOs.UserDTO.UserAuthResponseDTO;
import com.miniproject.splitwise.DTOs.UserDTO.UserSignUpRequestDTO;
import com.miniproject.splitwise.Exceptions.InvalidPhoneNumberExcepiton;
import com.miniproject.splitwise.Exceptions.PasswordLengthException;
import com.miniproject.splitwise.Exceptions.UserAccountPresentException;
import com.miniproject.splitwise.Exceptions.UserNotFoundException;
import com.miniproject.splitwise.Models.User;
import com.miniproject.splitwise.Services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserAuthController {
    private UserAuthService userAuthService;

    @Autowired
    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    public UserAuthResponseDTO signUpUser (UserSignUpRequestDTO signUpRequestDT0) {
        UserAuthResponseDTO signUpResponseDTO = new UserAuthResponseDTO();
        User user;

        try {
            user = userAuthService.signUpUser(signUpRequestDT0.getName(),
                    signUpRequestDT0.getEmail(), signUpRequestDT0.getPassword(),
                    signUpRequestDT0.getPhone());
            signUpResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            signUpResponseDTO.setUser(user);
        }catch (UserAccountPresentException | PasswordLengthException | InvalidPhoneNumberExcepiton e) {
            signUpResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            signUpResponseDTO.setFailureReason(e.getMessage());
        }
        return signUpResponseDTO;
    }
    public UserAuthResponseDTO loginUser (UserLoginRequestDTO loginRequestDTO) {
        UserAuthResponseDTO loginResponseDTO = new UserAuthResponseDTO();
        User user;

        try {
            user = userAuthService.loginUser(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
            loginResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            loginResponseDTO.setUser(user);
        }catch (UserNotFoundException e) {
            loginResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            loginResponseDTO.setFailureReason(e.getMessage());
        }

        return loginResponseDTO;
    }
}
