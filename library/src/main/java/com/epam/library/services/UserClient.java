package com.epam.library.services;



import com.epam.library.dto.UserRequest;
import com.epam.library.dto.UserResponse;
import com.epam.library.dto.UserUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "USERS" , path = "/v1/users" )
public interface UserClient {

    @GetMapping
    ResponseEntity<List<UserResponse>> getAllUsers();

    @GetMapping("/{username}")
    ResponseEntity<UserResponse> getByName(@PathVariable("username") String username);

    @PostMapping
    ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest);

    @PutMapping("/{username}")
    ResponseEntity<UserResponse> updateUser(@PathVariable("username") String username, @RequestBody UserUpdateRequest userRequest);

    @DeleteMapping("/{username}")
    ResponseEntity<Void> deleteUser(@PathVariable("username") String username);
}
