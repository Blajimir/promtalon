package ru.promtalon.service;

import java.util.Map;
import java.util.Set;

public interface BankingService {
    String getName();
    String getHost();
    Set<String> getKeysProperties();
    void setProperties(Map<String, String> props);
}
