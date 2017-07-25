package ru.promtalon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.promtalon.service.UserService;

/***
 * Инициализация базовых ролей в БД чере ApplicationListener
 */
@Component
public class InitializeBaseRoleApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        userService.initBaseRoles();
    }
}
