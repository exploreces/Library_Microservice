package com.epam.users.services;

import com.epam.users.Exceptions.InvalidRequestException;
import com.epam.users.dto.UserRequest;
import com.epam.users.dto.UserResponse;
import com.epam.users.dto.UserUpdateRequest;
import com.epam.users.entity.User;
import com.epam.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public List<UserResponse> getAllUsers() {
        List<User> users= userRepository.findAll() ;
        return users.stream().map(user -> objectMapper.convertValue(user , UserResponse.class)).toList();
    }


    public UserResponse getByName(String username) {
        User user = userRepository.findByName(username).orElseThrow(
                ()->  new InvalidRequestException("The username does not exist in database")
        );
        return objectMapper.convertValue(user , UserResponse.class);
    }


    public UserResponse saveUser(UserRequest userRequest) {
        User user = objectMapper.convertValue(userRequest, User.class);

        userRepository.findByEmail(userRequest.getEmail()).ifPresent(existingUser -> {
            throw new InvalidRequestException(
                    "A user already exists with the email: " + userRequest.getEmail()
            );
        });
        userRepository.save(user);
        return objectMapper.convertValue(user, UserResponse.class);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByName(username).orElseThrow(()->
                new InvalidRequestException("the username does not exist"));
          userRepository.delete(user);
    }

    public UserResponse update(String username, UserUpdateRequest userRequest) {
        User existUser = userRepository.findByName(username).orElseThrow(
                ()-> new InvalidRequestException("The user does not exist" +username)
        );
        existUser.setEmail(userRequest.getEmail());
        existUser.setName(userRequest.getName());
        userRepository.save(existUser);
        return objectMapper.convertValue(existUser , UserResponse.class);
    }
}
