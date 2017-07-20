package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.AcceptBankingOperation;

public interface AcceptBankingOperationDao extends JpaRepository<AcceptBankingOperation, Long> {
    AcceptBankingOperation findByAcceptCode(String code);
}
