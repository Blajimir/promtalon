package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.CouponOperation;

public interface CouponOperationDao extends JpaRepository<CouponOperation, Long> {
}
