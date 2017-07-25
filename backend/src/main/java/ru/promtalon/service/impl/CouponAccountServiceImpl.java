package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.ClientDao;
import ru.promtalon.dao.CouponAccountDao;
import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponAccount;
import ru.promtalon.service.CouponAccountService;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Service
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
        if (hasAccountWithActiveClientByClientId(client.getId())) {
            account.setClient(client);
            return accountDao.save(account);
        }
        return null;
    }

    @Override
    @Transactional
    public CouponAccount deleteAccount(long id) {
        CouponAccount account = accountDao.getOne(id);
        if (account!=null) {
            accountDao.delete(account);
        }
        return account;
    }

    @Override
    public CouponAccount getAccount(long id) {
        return accountDao.getOne(id);
    }

    @Override
    public CouponAccount getAccountByClient(long id) {
        return accountDao.getCouponAccountByClient_Id(id);
    }

    @Override
    public List<CouponAccount> getAllAccount() {
        return accountDao.findAll();
    }

    @Override
    public boolean hasAccountWithActiveClientByClientId(long id) {
        return accountDao.hasAccountWithActiveClientByClientId(id);
    }

    //TODO: написать логику пополнения
    @Override
    public void refill(long id, BigDecimal amount) {

    }
    //TODO: написать логику списания
    @Override
    public void debit(long id, BigDecimal amount) {

    }
}
