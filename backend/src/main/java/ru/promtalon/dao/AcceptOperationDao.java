package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.AcceptOperation;
import ru.promtalon.entity.Client;

import java.util.Date;
import java.util.List;

public interface AcceptOperationDao extends JpaRepository<AcceptOperation, Long> {
    AcceptOperation findByClientAndType(Client client, AcceptOperation.OperationType type);
    List<AcceptOperation> findAllByCreateDateIsLessThanEqual(Date date);
    List<AcceptOperation> findAllByClient(Client client);
}
