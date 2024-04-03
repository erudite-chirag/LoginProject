package com.example.ProjectLogin.controllers;

import com.example.ProjectLogin.models.UserModel;
import com.example.ProjectLogin.repositories.UserRepository;
import com.example.ProjectLogin.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/loginUser")
    public Object loginUser(@RequestBody UserModel userModel){
//        return userModel.getPassword();//UserModel is wat req body has
        try{
//            Optional<UserModel> userOptional = userRepository.findByUsername(userModel.getUsername());
//            if(userOptional.isPresent()){
            Optional<UserModel> userOptional = userRepository.findPasswordByUsername(userModel.getUsername());
            if(userOptional.isPresent()){
                String userPassword = userOptional.get().getPassword();
                String enteredPassword = String.valueOf(userModel.getPassword());

                if(userPassword.equals(enteredPassword)) {
                    return  ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(new ApiResponse("User Authenticated",true, null));
                }else{
                    return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ApiResponse("Invalid Password", true, null));
                }
            }
            else {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("User doesn't exist",false, null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.toString(), false, null));
        }
    }
}
