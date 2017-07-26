package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.AcceptCouponOperation;
import ru.promtalon.entity.CouponOperation;

import java.util.List;

public interface AcceptCouponOperationDao extends JpaRepository<AcceptCouponOperation, Long> {
    AcceptCouponOperation findByAcceptCode(String code);
    AcceptCouponOperation findByCouponOperation(CouponOperation operation);
    List<AcceptCouponOperation> findByCouponOperation_Sender_Id(Long id);
}
