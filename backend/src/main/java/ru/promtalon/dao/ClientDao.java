package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.Client;

public interface ClientDao extends JpaRepository<Client, Long> {
}
