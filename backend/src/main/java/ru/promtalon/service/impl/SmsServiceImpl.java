package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.promtalon.entity.Client;
import ru.promtalon.service.SettingService;
import ru.promtalon.service.SmsService;

public class SmsServiceImpl implements SmsService {
    private static final String prefix = "_SmsService";
    private String name;
    @Autowired
    SettingService settingService;


    @Override
    public String getName() {
        return null;
    }

    @Override
    public String sendSms(String phone, String text) {
        return null;
    }

    @Override
    public String sendSms(Client client, String text) {
        return null;
    }
}
