package com.robby.springtugasdia.service;

import com.robby.springtugasdia.dto.UserDTO;
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

    /* ----------------- POST ----------------- */

    public UserModel postUser(String userName, String userPassword, String userEmail, String userPhone, String userAddress, String userResume) {
        UserModel userModel = new UserModel();
        userModel.setUserName(userName);
        userModel.setUserPassword(userPassword);
        userModel.setUserEmail(userEmail);
        userModel.setUserPhone(userPhone);
        userModel.setUserAddress(userAddress);
        userModel.setUserResume(userResume);

        return userRepository.save(userModel);
    }

    public List<UserModel> postMultipleUser(List<UserModel> userModels) {
        return userRepository.saveAll(userModels);
    }

    /* ----------------- PUT ----------------- */

    public UserModel putUpdateUser(UserModel userModel) {
        int userId = userModel.getUserId();
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isEmpty()) {
            return null;
        }

        return userRepository.save(userModel);
    }

    /* ---------------- PATCH ---------------- */

    public UserModel patchUpdateUser(int userId, String userName) {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        UserModel userModel = userModelOptional.get();
        userModel.setUserName(userName);

        return userRepository.save(userModel);
    }

    /* ---------------- DELETE ---------------- */

    public boolean deleteUser(int userId) {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isEmpty()) {
            return false;
        }

        userRepository.deleteById(userId);
        return true;
    }

    /* ------------------- DTO ------------------- */

    public UserDTO dataWithDTO(int userId) {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isEmpty()) {
            return null;
        }

        return convertDTO(userModelOptional.get());
    }

    private UserDTO convertDTO(UserModel item) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(item.getUserName());
        userDTO.setUserEmail(item.getUserEmail());
        userDTO.setUserPhone(item.getUserPhone());
        userDTO.setUserAddress(item.getUserAddress());
        userDTO.setUserResume(item.getUserResume());

        return userDTO;
    }
}
