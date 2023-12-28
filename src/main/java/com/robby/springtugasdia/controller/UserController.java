package com.robby.springtugasdia.controller;

import com.robby.springtugasdia.model.UserModel;
import com.robby.springtugasdia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/data")
    public List<UserModel> getDataAllUser() {
        return userService.getDataAllUser();
    }

    @GetMapping("/data/name")
    public List<UserModel> getDataByNameUser(@RequestParam("userName") String userName) {
        return userService.getDataByNameUser(userName);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam("userName") String userName,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userPhone") String userPhone,
            @RequestParam("userAddress") String userAddress,
            @RequestParam("userResume") String userResume) {
        try {
            UserModel userModel = new UserModel();
            userModel.setUserName(userName);
            userModel.setUserPassword(userPassword);
            userModel.setUserEmail(userEmail);
            userModel.setUserPhone(userPhone);
            userModel.setUserAddress(userAddress);
            userModel.setUserResume(userResume);

            UserModel registeredUser = userService.registerUser(userModel);
            return ResponseEntity.ok(
                    "Registration successful \n" +
                            "User ID: " + registeredUser.getUserId() + "\n" +
                            "User Name: " + registeredUser.getUserName() + "\n" +
                            "User Email: " + registeredUser.getUserEmail() + "\n" +
                            "User Password: " + registeredUser.getUserPassword() + "\n" +
                            "User Phone: " + registeredUser.getUserPhone() + "\n" +
                            "User Address: " + registeredUser.getUserAddress() + "\n" +
                            "User Resume: " + registeredUser.getUserResume()
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userPassword") String userPassword
    ) {
        try {
            UserModel loggedInUser = userService.loginUser(userEmail, userPassword);
            return ResponseEntity.ok("Login successful. User ID: " + loggedInUser.getUserId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
