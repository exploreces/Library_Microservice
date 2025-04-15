package com.epam.users.controllers;

import com.epam.users.dto.UserRequest;
import com.epam.users.dto.UserResponse;
import com.epam.users.dto.UserUpdateRequest;
import com.epam.users.entity.User;
import com.epam.users.services.UserService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private  UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getByName(@Valid @PathVariable("username")String username){
        return ResponseEntity.ok(userService.getByName(username));
    }


    @PostMapping
    public ResponseEntity<UserResponse>saveUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.saveUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void>deleteUser(@Valid @PathVariable("username")String username){
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponse>updateUserProfile(@Valid @PathVariable("username") String username , @RequestBody UserUpdateRequest userRequest){
        UserResponse booksResponse = userService.update(username, userRequest);
        return  new ResponseEntity<>(booksResponse , HttpStatus.OK);

    }



}
