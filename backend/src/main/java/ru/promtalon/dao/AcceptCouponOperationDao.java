package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptCouponOperationDao extends JpaRepository<AcceptCouponOperation, Long> {
    AcceptCouponOperation findByCouponOperation_IdAndAcceptCode(Long id, String code);
    AcceptCouponOperation findByCouponOperation_Id(Long id);
}
