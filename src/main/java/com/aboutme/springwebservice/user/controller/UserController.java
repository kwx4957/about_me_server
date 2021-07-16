package com.aboutme.springwebservice.user.controller;

import com.aboutme.springwebservice.user.model.response.UserResponse;
import com.aboutme.springwebservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserResponse findUser(@PathVariable("id") Long userNo) {
        return userService.findUser(userNo);
    }

    @DeleteMapping("")
    public void deleteUser(@RequestParam Long userNo) {
        userService.deleteUser(userNo);
    }
}
