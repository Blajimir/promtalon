package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.Setting;

public interface SettingsDao extends JpaRepository<Setting, Long> {
    Setting findByName(String name);
}
