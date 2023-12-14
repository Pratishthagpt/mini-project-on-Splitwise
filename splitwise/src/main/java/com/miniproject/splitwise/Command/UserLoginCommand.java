package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.UserAuthController;
import com.miniproject.splitwise.DTOs.UserDTO.UserAuthResponseDTO;
import com.miniproject.splitwise.DTOs.UserDTO.UserLoginRequestDTO;
import com.miniproject.splitwise.DTOs.UserDTO.UserSignUpRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserLoginCommand implements Command{
//  Input format -  Login vinsmokesan@gmail.com namisswwaann

    @Autowired
    private UserAuthController userAuthController;

    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        String email = words.get(1);
        String password = words.get(2);

        UserLoginRequestDTO loginRequestDTO = new UserLoginRequestDTO();
        loginRequestDTO.setEmail(email);
        loginRequestDTO.setPassword(password);

        UserAuthResponseDTO responseDTO = userAuthController.loginUser(loginRequestDTO);
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return (words.size() == 3 && words.get(0).equals(CommandKeywords.login));
    }
}
