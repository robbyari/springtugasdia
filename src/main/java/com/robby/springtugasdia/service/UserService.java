package com.robby.springtugasdia.service;

import com.robby.springtugasdia.model.UserModel;
import com.robby.springtugasdia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getDataAllUser() {
        return userRepository.findByDataAllUser();
    }

    public List<UserModel> getDataByNameUser(String userName) {
        return userRepository.findByDataNameUser(userName);
    }

    public UserModel registerUser(UserModel userModel) {
        String userEmail = userModel.getUserEmail();
        Optional<UserModel> existingUser = userRepository.findByUserEmail(userEmail);

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already taken!");
        }

        return userRepository.save(userModel);
    }

    public UserModel loginUser(String userEmail, String userPassword) {
        UserModel user = userRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (user.getUserPassword().equals(userPassword)) {
            return user;
        } else {
            throw new RuntimeException("Invalid password!");
        }
    }

}
