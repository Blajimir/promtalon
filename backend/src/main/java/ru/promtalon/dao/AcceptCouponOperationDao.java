package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.promtalon.entity.AcceptCouponOperation;
import ru.promtalon.entity.CouponAccount;
import ru.promtalon.entity.CouponOperation;

import java.util.List;

public interface AcceptCouponOperationDao extends JpaRepository<AcceptCouponOperation, Long> {
    AcceptCouponOperation findByCouponOperation_IdAndAcceptCode(Long id, String code);
}
