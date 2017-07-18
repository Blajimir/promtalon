package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.User;

public interface UserDAO extends JpaRepository<User,Long>{
    User getUserByUsername(String name);
}
