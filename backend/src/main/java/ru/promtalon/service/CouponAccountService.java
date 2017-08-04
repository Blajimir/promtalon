package ru.promtalon.service;

import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponAccount;
import ru.promtalon.service.exception.CouponAccountException;

import java.math.BigDecimal;
import java.util.List;

public interface CouponAccountService {
    CouponAccount createAccount(Client client, BigDecimal amount);
    CouponAccount deleteAccount(long id);
    CouponAccount getAccount(long id);
    CouponAccount getEnabledAccountWithConfirmContacts(long id);
    CouponAccount getAccountByClient(long id);
    List<CouponAccount> getAllAccount();
    boolean hasAccountWithActiveClientByClientId(long id);
    //Пополнение
    void refill(long id, BigDecimal amount);
    //Списание
    void debit(long id, BigDecimal amount) throws CouponAccountException;



}
