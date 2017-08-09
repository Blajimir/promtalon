package ru.promtalon.service;

import ru.promtalon.entity.Setting;

public interface SettingService {
    void initBaseSettings();
    Setting getSetting(Long id);
    Setting getSettingByName(String name);
    Setting createSetting(Setting setting);
    Setting updateSetting(Setting setting);
    void deleteSetting(Long id);
}
