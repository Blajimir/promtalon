package ru.promtalon.service;

import ru.promtalon.entity.User;

public interface UserService {
    User getUser(long id);
    User updateUser(User user);
    User createUser(User user);
    void deleteUser(long id);
    User deleteUser(User user);
}
