package ru.promtalon.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.ClientDao;
import ru.promtalon.dao.CouponAccountDao;
import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponAccount;
import ru.promtalon.service.CouponAccountService;
import ru.promtalon.service.exception.CouponAccountException;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Service
@Log
public class CouponAccountServiceImpl implements CouponAccountService {

    @Autowired
    private CouponAccountDao accountDao;
    @Autowired
    private ClientDao clientDao;

    @Override
    @Transactional
    public CouponAccount createAccount(@NotNull Client client, BigDecimal amount) {
        CouponAccount account = new CouponAccount();
        account.setAmount(amount);
        client = clientDao.getActiveClient(client.getId());
        if (client!=null) {
            account.setClient(client);
            return accountDao.save(account);
        }
        return null;
    }

    @Override
    @Transactional
    public CouponAccount deleteAccount(long id) {
        CouponAccount account = accountDao.findOne(id);
        if (account!=null) {
            accountDao.delete(account);
        }
        return account;
    }

    @Override
    public CouponAccount getAccount(long id) {
        return accountDao.findOne(id);
    }

    @Override
    public CouponAccount getAccountByClient(long id) {
        return accountDao.getCouponAccountByClient_Id(id);
    }

    @Override
    public CouponAccount getEnabledAccountWithConfirmContacts(long id) {
        return accountDao.getEnabledAccountWithConfirmContacts(id);
    }

    @Override
    public List<CouponAccount> getAllAccount() {
        return accountDao.findAll();
    }

    @Override
    public boolean hasAccountWithActiveClientByClientId(long id) {
        return accountDao.hasAccountWithActiveClientByClientId(id);
    }

    @Override
    public void refill(long id, BigDecimal amount){
        if(id>0&&amount.intValue()>0){
            CouponAccount account = accountDao.findOne(id);
            BigDecimal a = account.getAmount();
            account.setAmount(a.add(amount));
            accountDao.save(account);
        }/*else{

        }*/
    }
    /*
    * Реализация алгоритма списания купонов со счета
    * */
    @Override
    public void debit(long id, BigDecimal amount) throws CouponAccountException {
        if(id>0&&amount.intValue()>0){
            CouponAccount account = accountDao.findOne(id);
            BigDecimal acc = account.getAmount();

            if(acc.longValue()>=amount.longValue()){
                account.setAmount(acc.subtract(amount));
                accountDao.save(account);
            }else{
                String msg = String.format("счет id:%d не может содержать меньше чем " +
                                "количество списовыемых купонов! состояние счета:%d количество списываемых средств:%d",
                        id,acc.longValue(),amount.longValue());
                log.warning(msg);
                throw new CouponAccountException(msg);
            }
        }else{
            String msg = String.format("account id and amount should not be null! id:%d amount:%s",id,amount);
            log.warning(msg);
            throw new CouponAccountException(msg);
        }
    }
}
