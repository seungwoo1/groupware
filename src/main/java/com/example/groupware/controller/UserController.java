package com.example.groupware.controller;

import com.example.groupware.dto.UserRequestDto;
import com.example.groupware.entity.User;
import com.example.groupware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody UserRequestDto requstDto) {
        return userService.saveUser(requstDto);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return  userService.getUserById(id);
    }
}
