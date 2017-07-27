package ru.promtalon.service;

import ru.promtalon.entity.Client;
public interface MailService {
    String getName();
    String sendMail(String email, String subject, String content);
    String sendMail(Client client, String subject, String content);
}
