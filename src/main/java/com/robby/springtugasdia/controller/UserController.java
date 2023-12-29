package com.robby.springtugasdia.controller;

import com.robby.springtugasdia.dto.UserDTO;
import com.robby.springtugasdia.model.UserModel;
import com.robby.springtugasdia.service.FileService;
import com.robby.springtugasdia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

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


    /* ------------------- POST ------------------- */

    @PostMapping("/createUser")
    public UserModel postCreateUser(
            @RequestParam("userName") String userName,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userPhone") String userPhone,
            @RequestParam("userAddress") String userAddress,
            @RequestParam("userResume") String userResume
    ) {
        return userService.postUser(
                userName,
                userPassword,
                userEmail,
                userPhone,
                userAddress,
                userResume
        );
    }

    @PostMapping("/createMultipleUser")
    public List<UserModel> postCreateMultipleUser(@RequestBody List<UserModel> userModels) {
        return userService.postMultipleUser(userModels);
    }

    /* ------------------- PUT ------------------- */

    @PutMapping("/putUpdateUser")
    public UserModel putUpdateUser(@RequestBody UserModel userModel) {
        return userService.putUpdateUser(userModel);
    }

    /* ------------------- PATCH ------------------- */

    @PatchMapping("/patchUpdateUser")
    public UserModel patchUpdateUser(
            @RequestParam("userId") int userId,
            @RequestParam("userName") String userName
    ) {
        return userService.patchUpdateUser(userId, userName);
    }

    /* ------------------- DELETE ------------------- */

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int userId) {
        boolean response = userService.deleteUser(userId);

        if (response) {
            return "Delete success";
        } else {
            return "Delete failed";
        }
    }

    /* ------------------- MULTIPART ------------------- */

    @PostMapping("/uploadFile")
    public boolean uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.saveFile(file);
        return true;
    }

    /* ------------------- DTO ------------------- */

    @GetMapping("/datadto")
    public UserDTO getDataWithDTO(@RequestParam("userId") int userId) {
        return userService.dataWithDTO(userId);
    }
}
