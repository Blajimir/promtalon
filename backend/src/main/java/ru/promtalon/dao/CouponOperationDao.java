package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.CouponOperation;

public interface CouponOperationDao extends JpaRepository<CouponOperation, Long> {
    CouponOperation getByIdAndSender_Client_Id(Long id, Long clientId);
}
