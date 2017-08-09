package ru.promtalon.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.promtalon.entity.User;

public interface MyUserDetailsService extends UserDetailsService {
    User getCurrentUser();
    String getCurrentUsername();
}
