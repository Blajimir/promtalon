package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.promtalon.dao.RoleDAO;
import ru.promtalon.dao.UserDAO;
import ru.promtalon.entity.Role;
import ru.promtalon.entity.User;
import ru.promtalon.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void initBaseRoles() {
        if (!this.roleDAO.existRoleByName("ROLE_USER")) {
            roleDAO.save(new Role("ROLE_USER"));
        }
        if (!this.roleDAO.existRoleByName("ROLE_ADMIN")) {
            roleDAO.save(new Role("ROLE_ADMIN"));
        }
    }

    @Override
    public User getUser(long id) {
        return userDAO.findOne(id);
    }

    @Override
    public User getUserByUsername(String name) {
        return userDAO.getUserByUsername(name);
    }

    //TODO: Данная функция нужна реализовывает логику регистрации нового пользователя
    //ее необходимо усложнить реализовав подтверждение регистрации
    @Override
    public User regNewUser(User user) {
        user.setRoles(Stream.of(roleDAO.findRoleByName("ROLE_USER")).collect(Collectors.toList()));
        user.setEnable(true);
        return createUser(user);
    }

    @Override
    public User updateUser(User user) {
        if (userDAO.exists(user.getId())) {
            return userDAO.save(user);
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        user.setId(0);
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        }
        return userDAO.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userDAO.delete(id);
    }

    @Override
    public User deleteUser(User user) {
        userDAO.delete(user);
        return user;
    }

    @Override
    public User invokeRole(User user, Role role) {
        if (user != null && userDAO.exists(user.getId()) && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userDAO.save(user);
        }
        return user;
    }

    @Override
    public User revokeRole(User user, Role role) {
        if (user != null && userDAO.exists(user.getId()) && user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userDAO.save(user);
        }
        return user;
    }
}
