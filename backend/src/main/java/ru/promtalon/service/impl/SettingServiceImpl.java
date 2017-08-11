package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.SettingsDao;
import ru.promtalon.entity.Setting;
import ru.promtalon.service.SettingService;

import java.util.HashMap;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingsDao settingsDao;

    @Override
    public void initBaseSettings() {
        Setting systemSetting = new Setting();
        systemSetting.setName("system");
        systemSetting.setProps(new HashMap<>());
        systemSetting.getProps().put("Promo_commission","1");
        //systemSetting.getProps().put("accesscode_length","8");
        systemSetting.setId(0);
        settingsDao.save(systemSetting);
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
    public String getPropertyByName(String settingName, String property) {
        String result = null;
        Setting setting = getSettingByName(settingName);
        if(setting!=null){
            result = setting.getProps().get(property);
        }
        return result;
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
