package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.UserDao;
import ru.promtalon.entity.User;
import ru.promtalon.service.MyUserDetailsService;

import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserDetailsServiceSecurityImpl implements MyUserDetailsService {

    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(String.format("User with name: %s not found!",username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                user.isEnabled(),true,true,true,
                user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toSet()));
    }

    @Override
    public User getCurrentUser() {
        String username = getCurrentUsername();
        return userDao.getUserByUsername(username);
    }

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
