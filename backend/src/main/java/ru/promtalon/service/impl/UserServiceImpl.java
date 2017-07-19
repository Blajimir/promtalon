package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.promtalon.dao.RoleDao;
import ru.promtalon.dao.UserDao;
import ru.promtalon.entity.Role;
import ru.promtalon.entity.User;
import ru.promtalon.service.UserService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void initBaseRoles() {
        if (!this.roleDao.existRoleByName("ROLE_USER")) {
            roleDao.save(new Role("ROLE_USER"));
        }
        if (!this.roleDao.existRoleByName("ROLE_ADMIN")) {
            roleDao.save(new Role("ROLE_ADMIN"));
        }
    }

    @Override
    public User getUser(long id) {
        return userDao.findOne(id);
    }

    @Override
    public User getUserByUsername(String name) {
        return userDao.getUserByUsername(name);
    }

    //TODO: Данная функция нужна реализовывает логику регистрации нового пользователя
    //ее необходимо усложнить реализовав подтверждение регистрации
    @Override
    public User regNewUser(User user) {
        user.setRoles(Stream.of(roleDao.findRoleByName("ROLE_USER")).collect(Collectors.toList()));
        user.setEnable(true);
        return createUser(user);
    }

    @Override
    public User updateUser(User user) {
        User lastUser = userDao.getOne(user.getId());
        if (lastUser!=null) {
            user.
            return userDao.save(user);
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        user.setId(0);
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        }
        return userDao.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userDao.delete(id);
    }

    @Override
    public User deleteUser(User user) {
        userDao.delete(user);
        return user;
    }

    @Override
    public User invokeRole(User user, Role role) {
        if (user != null && userDao.exists(user.getId()) && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userDao.save(user);
        }
        return user;
    }

    @Override
    public User revokeRole(User user, Role role) {
        if (user != null && userDao.exists(user.getId()) && user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userDao.save(user);
        }
        return user;
    }
}
