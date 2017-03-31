package ru.promtalon.service;

import ru.promtalon.entity.Role;
import ru.promtalon.entity.User;

public interface UserService {
    User getUser(long id);
    User updateUser(User user);
    User createUser(User user);
    void deleteUser(long id);
    User deleteUser(User user);
    User invokeRole(User user, Role role);
    User revokeRole(User user, Role role);
}
