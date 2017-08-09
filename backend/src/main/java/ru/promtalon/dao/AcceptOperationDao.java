package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.AcceptOperation;
import ru.promtalon.entity.Client;

public interface AcceptOperationDao extends JpaRepository<AcceptOperation, Long> {
    AcceptOperation findByClientAndType(Client client, AcceptOperation.OperationType type);
}
