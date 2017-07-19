package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.PromoOperation;

public interface PromoOperationDao extends JpaRepository<PromoOperation, Long> {
}
