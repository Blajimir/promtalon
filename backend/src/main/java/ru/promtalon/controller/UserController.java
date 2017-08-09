package ru.promtalon.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.promtalon.entity.Role;
import ru.promtalon.entity.User;
import ru.promtalon.service.MyUserDetailsService;
import ru.promtalon.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@Log
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    MyUserDetailsService userDetailsService;

    private User getCurrentUser() {
        return userService.getUserByUsername(userDetailsService.getCurrentUsername());
    }

    private String getIp() {
        WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return details.getRemoteAddress();
    }

    @PostMapping(value = "/api/user/login")
    @ResponseBody
    public Object login(@RequestBody @NotNull User user) {
        try {
            AuthenticationManager authenticationManager = authenticationManagerBuilder.getOrBuild();
            Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            log.info(String.format("Successful login by user: %s, from ip: %s", user.getUsername(), getIp()));
            return Collections.singletonMap("Success", true);
        } catch (AuthenticationException e) {
            log.info(String.format("Fails login by user: %s, from ip: %s error:%s",
                    user.getUsername(), getIp(),e.getMessage()));
            return Collections.singletonMap("Error", e.getMessage());
        }
    }

    @GetMapping(value = "/api/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        User user = getCurrentUser();
        if (user != null) {
            new SecurityContextLogoutHandler().logout(request, response,
                    SecurityContextHolder.getContext().getAuthentication());
            log.info(String.format("Successful logout by user: %s, from ip: %s", user.getUsername(), getIp()));
        }
    }

    @Secured("ADMIN")
    @GetMapping(value = "/api/user/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @Secured("ADMIN")
    @GetMapping(value = "/api/user")
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Secured("ADMIN")
    @PostMapping(value = "/api/user")
    @ResponseBody
    public User updateUser(@Valid @RequestBody User newData) {
        if (newData != null && newData.getId() > 0) {
            User user = userService.getUser(newData.getId());
            newData = userService.updateUserIgnoreFields(user, newData,
                    Stream.of("Password", "Roles").collect(Collectors.toList()));
            log.info(String.format("Successful update by user: %s, from ip: %s for User-Object:%s",
                    userDetailsService.getCurrentUsername(), getIp(), newData.toString()));
        } else {
            newData = null;
            log.info(String.format("Wrong data with updateUser by user: %s, from ip: %s for User-Object:%s",
                    userDetailsService.getCurrentUsername(), getIp(), newData.toString()));
        }
        return newData;
    }

    @Secured("ADMIN")
    @PutMapping(value = "/api/user")
    @ResponseBody
    public User addUser(@Valid @RequestBody User user) {
        log.info("User body: " + user);
        user = userService.createUser(user);
        if (user != null) {
            log.info(String.format("Successful addUser by user: %s, from ip: %s for User-Object:%s",
                    userDetailsService.getCurrentUsername(), getIp(), user.toString()));
        }
        return user;
    }

    @Secured("ADMIN")
    @DeleteMapping(value = "/api/user/{id}")
    @ResponseBody
    public User deleteUser(@PathVariable Long id) {
        User user = getUser(id);
        if (user != null) {
            userService.deleteUser(id);
            log.info(String.format("Successful deleteUser by user: %s, from ip: %s for User-Object:%s",
                    userDetailsService.getCurrentUsername(), getIp(), user.toString()));
        }
        return user;
    }

    @Secured("ADMIN")
    @PostMapping(value = "/api/user/revoke/{id}")
    @ResponseBody
    public User revokeUserRole(@PathVariable Long id, @RequestBody Role role) {
        User user = getUser(id);
        if (user != null && role != null) {
            user = userService.revokeRole(user, role);
            log.info(String.format("Successful revokeUserRole by user: %s, from ip: %s for User-Object:%s",
                    userDetailsService.getCurrentUsername(), getIp(), user.toString()));
        }
        return user;
    }

    @Secured("ADMIN")
    @PostMapping(value = "/api/user/invoke/{id}")
    @ResponseBody
    public User invokeUserRole(@PathVariable Long id, @RequestBody Role role) {
        User user = getUser(id);
        if (user != null && role != null) {
            user = userService.invokeRole(user, role);
            log.info(String.format("Successful invokeUserRole by user: %s, from ip: %s for User-Object:%s",
                    userDetailsService.getCurrentUsername(), getIp(), user.toString()));
        }
        return user;
    }

}
