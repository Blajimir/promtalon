package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.SettingsDao;
import ru.promtalon.entity.Setting;
import ru.promtalon.service.SettingService;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingsDao settingsDao;

    @Override
    public void initBaseSettings() {

    }

    @Override
    public Setting getSetting(Long id) {
        return settingsDao.findOne(id);
    }

    @Override
    public Setting getSettingByName(String name) {
        return settingsDao.findByName(name);
    }

    @Override
    public Setting createSetting(Setting setting) {
        if(setting!=null){
            setting.setId(0);
            return settingsDao.save(setting);
        }
        return null;
    }

    @Override
    public Setting updateSetting(Setting setting) {
        if(setting!=null&&setting.getId()!=0){
            return settingsDao.save(setting);
        }
        return null;
    }

    @Override
    public void deleteSetting(Long id) {
        settingsDao.delete(id);
    }
}
