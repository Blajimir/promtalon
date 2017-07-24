package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.promtalon.entity.Client;

public interface ClientDao extends JpaRepository<Client, Long> {
    Client getClientByUser_Username(String username);
    Client getClientByIdAndUserNotNull(Long id);
    @Query("select c from Client c where c.user.enabled = true and c.id = ?1")
    Client getActiveClient(long id);
    @Query("select c from Client c where c.user.enabled = false and c.id = ?1")
    Client getDeactiveClient(long id);
}
