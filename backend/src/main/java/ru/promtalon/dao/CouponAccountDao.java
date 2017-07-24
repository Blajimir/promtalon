package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.promtalon.entity.CouponAccount;

public interface CouponAccountDao extends JpaRepository<CouponAccount, Long> {
    CouponAccount getCouponAccountByClient_Id(Long id);

    @Query(value = "select case when count(c)>0 then true else false end from CouponAccount c " +
            "where c.client.user is not null and c.client.user.enabled = true and c.client.id = ?1")
    boolean hasClientActiveByAcctId(Long id);
}
