package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.promtalon.entity.CouponAccount;

public interface CouponAccountDao extends JpaRepository<CouponAccount, Long> {
    CouponAccount getCouponAccountByClient_Id(Long id);

    @Query(value = "select case when count(c)>0 then true else false end from CouponAccount c " +
            "where c.client.user.enabled = true and c.client.id = ?1")
    boolean hasAccountWithActiveClientByClientId(Long id);
    @Query(value = "select c from CouponAccount c " +
            "where (c.client.contact.emailAccept = true or c.client.contact.phoneAccept = true) " +
            "and c.client.user.enabled = true  and c.client.id = ?1")
    CouponAccount getEnabledAccountWithConfirmContacts(long client_id);
}
