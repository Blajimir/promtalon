package ru.promtalon.service;

import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponAccount;

import java.math.BigDecimal;

public interface CouponAccountService {
    CouponAccount createAccount(Client client, BigDecimal amount);
    CouponAccount deleteAccount(long id);
    CouponAccount getAccount(long id);
    CouponAccount getAccountByClient(long id);
    //Пополнение
    void refill(long id, BigDecimal amount);
    //Списание
    void debit(long id, BigDecimal amount);



}
