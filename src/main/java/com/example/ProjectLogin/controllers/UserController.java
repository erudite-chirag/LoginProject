package com.example.ProjectLogin.controllers;

import com.example.ProjectLogin.models.UserModel;
import com.example.ProjectLogin.repositories.UserRepository;
import com.example.ProjectLogin.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUser")     //Create User
    public Object createUser(@RequestBody UserModel userModel) {
        try {
            userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("User Created Successfully", true, userModel));
//            return "User Created.";
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.toString(), false, null));
        }
    }

    @PostMapping("/getUser")   //Get Single User by Username
    public ResponseEntity<ApiResponse> getUser(@RequestBody UserModel userModel) {
        try {
            Optional<UserModel> userOptional = userRepository.findByUsername(userModel.getUsername());
            //                if (userOptional.isPresent()) {
            //                    UserModel user = userOptional.get();
            //                    return ResponseEntity.status(HttpStatus.OK)
            //                            .body(new ApiResponse("User Found", true, user));
            return userOptional.map(model -> ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("User Found", true, model))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User Not Found", false, null)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.toString(), false, null));
        }
    }

    @GetMapping("/getAllUsers") //Get All Users
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<UserModel> userList = userRepository.findAll();
            if(!userList.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse("Found Users", true, userList));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No User Found", false, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.toString(), false, null));
        }
    }

    @PutMapping("/updateUser")  // Update User by Username
    public String updateUser(@RequestBody UserModel userModel) {
        try {
            Optional<UserModel> userOptional = userRepository.findByUsername(userModel.getUsername());
            if (userOptional.isPresent()) {
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