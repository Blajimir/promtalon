package ru.promtalon.service;

import ru.promtalon.entity.Client;

public interface SmsService {
    String getName();
    String sendSms(String phone, String text);
    String sendSms(Client client, String text);
}
