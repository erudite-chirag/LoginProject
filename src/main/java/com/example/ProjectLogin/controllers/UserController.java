package com.example.ProjectLogin.controllers;

import com.example.ProjectLogin.models.UserModel;
import com.example.ProjectLogin.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUser")     //Create User
    public String createUser(@RequestBody UserModel userModel) {
        try {
            userRepository.save(userModel);
            return "User Created.";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @PostMapping("/getUser")   //Get Single User by Username
    public String getUser(@RequestBody UserModel userModel) {
        try {
            Optional<UserModel> userOptional = userRepository.findByUsername(userModel.getUsername());
            if (userOptional.isPresent()) {
                UserModel user = userOptional.get();
                String userDetails = "Id: " + user.getId() +
                        ", \nUsername: " + user.getUsername() +
                        ", \nFirst Name: " + user.getFirstName() +
                        ", \nLast Name: " + user.getLastName() +
                        ", \nPhone Number: " + user.getPhoneNumber();
                return userDetails;
            } else {
                return "User Not Found";
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    @GetMapping("/getAllUsers") //Get All Users
    public String getAllUsers() {
        try {
            List<UserModel> allUsers = userRepository.getAllUsers();
            if(allUsers.isEmpty()){
                return "No User Found";
            }else{
                StringBuilder userDetails = new StringBuilder();
                for (UserModel user : allUsers) {
                    userDetails.append("Id: ").append(user.getId())
                            .append(", Username: ").append(user.getUsername())
                            .append(", First Name: ").append(user.getFirstName())
                            .append(", Last Name: ").append(user.getLastName())
                            .append(", Phone Number: ").append(user.getPhoneNumber())
                            .append("\n");
                }
                return userDetails.toString();
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    @PutMapping("/updateUser")  // Update User by Username
    public String updateUser(@RequestBody UserModel userModel) {
        try {
            Optional<UserModel> userOptional = userRepository.findByUsername(userModel.getUsername());
            if (userOptional.isPresent()) {
//                user.setFirstName(userModel.getFirstName());
//                user.setLastName(userModel.getLastName());
//                user.setPassword(userModel.getPassword());
//                userRepository.save(user);  // Save the updated user
                userRepository.updateUserDetails(userModel.getUsername(), userModel.getFirstName(), userModel.getLastName(), userModel.getPassword());
                UserModel user = userOptional.get();
                String userDetails = "Id: " + user.getId() +
                        ", \nUsername: " + user.getUsername() +
                        ", \nFirst Name: " + user.getFirstName() +
                        ", \nLast Name: " + user.getLastName() +
                        ", \nPhone Number: " + user.getPhoneNumber();
                return userDetails;
            } else {
                return "User Not Found";
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestBody UserModel userModel) {
        try {
            Optional<UserModel> userOptional= userRepository.findByUsername(userModel.getUsername());
            System.out.println(userOptional);
            if(userOptional.isPresent()){
                UserModel user = userOptional.get();
                userRepository.deleteByUsername(user.getUsername());
                return "Sucessfully Deleted";
            }
            else{
                return "User Not Found";
            }
        } catch (Exception e) {
            return e.toString();
        }
    }
}