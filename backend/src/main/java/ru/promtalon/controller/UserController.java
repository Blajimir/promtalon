package ru.promtalon.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.promtalon.entity.User;
import ru.promtalon.service.UserService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@Log
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/api/user/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping(path = "/api/user")
    @ResponseBody
    public User updateUser(@RequestBody User user){
        userService.getUserByUsername(user.getUsername());
        return user;
    }

    @PutMapping(path = "/api/user")
    @ResponseBody
    public User addUser(@RequestBody User user){
        log.info("User body: "+user);
        userService.createUser(user);
        return user;
    }
}
