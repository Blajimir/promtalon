package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.AcceptCouponOperation;

public interface AcceptCouponOperationDao extends JpaRepository<AcceptCouponOperation, Long> {
    AcceptCouponOperation findByAcceptCode(String code);
}
