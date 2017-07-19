package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.BankingOperation;

public interface BankingOperationDao extends JpaRepository<BankingOperation,Long> {
}
