package com.miniproject.splitwise.Command;

import com.miniproject.splitwise.Controllers.UserAuthController;
import com.miniproject.splitwise.DTOs.UserDTO.UserAuthResponseDTO;
import com.miniproject.splitwise.DTOs.UserDTO.UserSignUpRequestDTO;
import com.miniproject.splitwise.Repositories.UserRepository;
import com.miniproject.splitwise.Services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSignUpCommand implements Command{
//  Input format -  Register vinsmokesanji 003 vinsmokesan@gmail.com namisswwaann

    @Autowired
    private UserAuthController userAuthController;


    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        String username = words.get(1);
        String phone = words.get(2);
        String email = words.get(3);
        String password = words.get(4);

        UserSignUpRequestDTO signUpRequestDTO = new UserSignUpRequestDTO();
        signUpRequestDTO.setName(username);
        signUpRequestDTO.setPhone(phone);
        signUpRequestDTO.setEmail(email);
        signUpRequestDTO.setPassword(password);

        if (userAuthController.signUpUser(signUpRequestDTO) != null) {
            UserAuthResponseDTO responseDTO = userAuthController.signUpUser(signUpRequestDTO);
        }
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return (words.size() == 5 && words.get(0).equals(CommandKeywords.register));
    }
}
