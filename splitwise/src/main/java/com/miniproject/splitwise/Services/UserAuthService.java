package com.miniproject.splitwise.Services;

import com.miniproject.splitwise.Exceptions.InvalidPhoneNumberExcepiton;
import com.miniproject.splitwise.Exceptions.PasswordLengthException;
import com.miniproject.splitwise.Exceptions.UserAccountPresentException;
import com.miniproject.splitwise.Exceptions.UserNotFoundException;
import com.miniproject.splitwise.Models.User;
import com.miniproject.splitwise.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService {
    private UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUpUser(String name, String email, String password, String phone) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAccountPresentException("Account with this email is already present!!" +
                    "Please go to login details.");
        }
        if (password.length() < 8) {
            throw new PasswordLengthException("Password should be greator than or equal to 8 characters");
        }
        if (phone.length() != 10 || !phone.matches("\\d+")) {    // phone should have digits > 10 and should only contain digits
            throw new InvalidPhoneNumberExcepiton("Phone number should have 10 digits and it should contain digits only.");
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);

        return userRepository.save(newUser);
    }

    public User loginUser (String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Sorry!! No user account registered with given email Id." +
                    "Please enter the valid email or go to sign up option.");
        }
        User user = userOptional.get();
        return user;
    }
}
