package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.AcceptCouponOperationDao;
import ru.promtalon.entity.AcceptCouponOperation;
import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponOperation;
import ru.promtalon.service.AcceptCouponOperationService;
import ru.promtalon.service.CouponOperationService;
import ru.promtalon.service.MailService;
import ru.promtalon.service.SmsService;
import ru.promtalon.util.NumberCodeGenerator;

import javax.validation.constraints.NotNull;
import java.util.TreeMap;

import static ru.promtalon.entity.CouponOperation.OperationType.*;

@Service
public class AcceptCouponOperationServiceImpl implements AcceptCouponOperationService {

    @Autowired
    private AcceptCouponOperationDao acceptDao;
    @Autowired
    private CouponOperationService operationService;
    @Autowired
    private NumberCodeGenerator numberCodeGenerator;
    @Autowired
    private SmsService smsService;
    @Autowired
    private MailService mailService;

    @Override
    public AcceptCouponOperation addAcceptCouponOperation(@NotNull CouponOperation operation) {
        AcceptCouponOperation result = null;
        result = new AcceptCouponOperation();
        result.setCouponOperation(operation);
        TreeMap<Long, String> code = getUniqCode(operation);
        result.setAcceptCode(code.firstEntry().getValue());
        result = acceptDao.save(result);
        smsService.sendSms(operation.getSender().getClient(), transformCodeForSender(code));
        mailService.sendMail(operation.getSender().getClient(), "confirm code", transformCodeForSender(code));
        return result;
    }

    @Override
    public AcceptCouponOperation refreshAcceptCouponOperation(CouponOperation operation) {
        return null;
    }

    @Override
    public void deleteAcceptCouponOperation(CouponOperation operation) {
        if (operation!=null){
        acceptDao.delete(acceptDao.findByCouponOperation_Id(operation.getId()));
        }
    }

    @Override
    public CouponOperation acceptCouponOperation(Client client, long operation_id, String code) {
        AcceptCouponOperation acceptOperation = acceptDao.findByCouponOperation_IdAndAcceptCode(operation_id, code);
        CouponOperation operation = null;
        if (acceptOperation != null) {
            operation = acceptOperation.getCouponOperation();
            if ((operation.getType() == TRANSFER && equilsClients(client, operation.getSender().getClient())) ||
                    (operation.getType() == PAYMENT && equilsClients(client, operation.getReceiver().getClient()))) {
                acceptDao.delete(acceptOperation);
            } else {
                operation = null;
            }
        }
        return operation;
    }

    //@Lock(LockModeType.READ)
    private TreeMap<Long, String> getUniqCode(CouponOperation operation) {
        TreeMap<Long, String> singletonTreeMap = new TreeMap<>();
        singletonTreeMap.put(operation.getId(), numberCodeGenerator.getToken());
        return singletonTreeMap;
    }

    private String transformCodeForSender(TreeMap<Long, String> code) {
        return code.firstEntry().getValue() + "-" + code.firstKey();
    }

    private boolean equilsClients(Client a, Client b) {
        boolean result = false;
        if (a != null && b != null && a.getId() == b.getId()) result = true;
        return result;
    }
}
