package ru.promtalon.service;

import ru.promtalon.entity.User;

public interface MailService {
    String getName();
    String sendMail(String email, String subject, String content);
    String sendMail(User user, String subject, String content);
}
