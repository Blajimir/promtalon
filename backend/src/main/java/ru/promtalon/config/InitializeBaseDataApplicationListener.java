package ru.promtalon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.promtalon.service.SettingService;
import ru.promtalon.service.UserService;

/**
 * Инициализация базовых данных в БД чере ApplicationListener
 */
@Component
public class InitializeBaseDataApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;
    @Autowired
    private SettingService settingService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        userService.initBaseRoles();
        settingService.initBaseSettings();
    }
}
