package ru.promtalon.service;

import ru.promtalon.entity.User;

public interface SmsService {
    String getName();
    String sendSms(String phone, String text);
    String sendSms(User user, String text);
}
