package ru.promtalon.service.impl;

import org.springframework.stereotype.Service;
import ru.promtalon.entity.Client;
import ru.promtalon.service.MailService;

@Service
public class MailServiceImpl implements MailService {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String sendMail(String email, String subject, String content) {
        return null;
    }

    @Override
    public String sendMail(Client client, String subject, String content) {
        return null;
    }
}
