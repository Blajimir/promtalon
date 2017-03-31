package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.promtalon.dao.UserDAO;
import ru.promtalon.entity.Role;
import ru.promtalon.entity.User;
import ru.promtalon.service.UserService;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public User getUser(long id) {
        return userDAO.findOne(id);
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
