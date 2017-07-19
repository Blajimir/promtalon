package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.CouponAccount;

public interface CouponAccountDao extends JpaRepository<CouponAccount, Long> {
}
