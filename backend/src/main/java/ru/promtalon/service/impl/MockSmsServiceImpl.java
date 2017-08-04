package ru.promtalon.service.impl;

import org.springframework.stereotype.Service;
import ru.promtalon.entity.Client;
import ru.promtalon.service.SmsService;

import java.util.HashMap;
import java.util.Map;

@Service
public class MockSmsServiceImpl implements SmsService {
    private String code;
    private Map<Long,String> codes = new HashMap<>();
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
        code = text;
        codes.put(client.getId(), text);
        return code;
    }

    public Map<Long, String> getCodes() {
        return codes;
    }

    public String getCode(){
        return code;
    }
}
