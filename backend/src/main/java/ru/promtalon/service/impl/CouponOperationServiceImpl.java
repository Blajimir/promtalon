package ru.promtalon.service.impl;

import com.sun.istack.internal.Nullable;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.CouponOperationDao;
import ru.promtalon.entity.AcceptCouponOperation;
import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponAccount;
import ru.promtalon.entity.CouponOperation;
import ru.promtalon.service.AcceptCouponOperationService;
import ru.promtalon.service.CouponAccountService;
import ru.promtalon.service.CouponOperationService;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Service
@Log
public class CouponOperationServiceImpl implements CouponOperationService {

    @Autowired
    private CouponOperationDao operationDao;
    @Autowired
    private CouponAccountService accountService;
    @Autowired
    private AcceptCouponOperationService acceptService;

    @Override
    public CouponOperation addOperation(@NotNull CouponOperation couponOperation) {
        couponOperation.setId(0);
        return operationDao.save(couponOperation);
    }

    @Override
    public CouponOperation saveOperation(@NotNull CouponOperation couponOperation) {
        if (couponOperation.getId() > 0) {
            return operationDao.save(couponOperation);
        }
        return null;
    }

    @Override
    public CouponOperation getOperation(long id) {
        if (id > 0) return operationDao.getOne(id);
        return null;
    }

    @Override
    public CouponOperation deleteOperation(@NotNull CouponOperation couponOperation) {
        return deleteOperation(couponOperation.getId());
    }

    @Override
    public CouponOperation deleteOperation(long id) {
        CouponOperation operation = getOperation(id);
        if (operation != null) operationDao.delete(operation);
        return operation;
    }

    @Override
    public CouponOperation changeStatusOperation(@NotNull CouponOperation couponOperation, @NotNull CouponOperation.OperationStatus status) {
        couponOperation = getOperation(couponOperation.getId());
        if (couponOperation != null && status != couponOperation.getStatus()) {
            couponOperation.setStatus(status);
            couponOperation = saveOperation(couponOperation);
        }
        return couponOperation;
    }

    @Override
    public List<CouponOperation> getAllOperations() {
        return operationDao.findAll();
    }

    @Override
    public List<CouponOperation> getAllOperationsBySender(@NotNull CouponAccount sender, @Nullable CouponOperation.OperationType type) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withMatcher("sender",
                ExampleMatcher.GenericPropertyMatcher::contains);
        if (type != null) matcher.withMatcher("type", ExampleMatcher.GenericPropertyMatcher::contains);
        matcher.withIgnoreNullValues();
        CouponOperation operation = new CouponOperation();
        operation.setSender(sender);
        Example<CouponOperation> example = Example.of(operation, matcher);
        return operationDao.findAll(example);
    }

    @Override
    public List<CouponOperation> getAllOperationsByReceiver(CouponAccount receiver, CouponOperation couponOperation) {

        return null;
    }

    @Override
    public List<CouponOperation> getAllSenderOperationsByStatus(Client sender, CouponOperation.OperationStatus status) {
        return null;
    }

    @Override
    public List<CouponOperation> getAllSenderOperationsByType(Client sender, CouponOperation.OperationType type) {
        return null;
    }

    @Override
    public List<CouponOperation> getAllSenderOperationsByStatusAndType(Client sender, CouponOperation.OperationStatus status) {
        return null;
    }

    @Override
    @Transactional
    public CouponOperation addTransferOperation(@NotNull Client sender, @NotNull Client receiver, long amount) {
        CouponOperation result = null;
        CouponAccount acctSender = accountService.getEnabledAccountWithConfirmContacts(sender.getId());
        CouponAccount acctReceiver = accountService.getEnabledAccountWithConfirmContacts(receiver.getId());
        if (acctSender != null && acctReceiver != null && amount > 0) {
            try {
                accountService.debit(sender.getId(), new BigDecimal(amount));
                CouponOperation operation = new CouponOperation();
                operation.setStatus(CouponOperation.OperationStatus.FREEZE);
                operation.setType(CouponOperation.OperationType.TRANSFER);
                operation.setSender(acctSender);
                operation.setReceiver(acctReceiver);
                long commission = 1;//TODO: Написать алгоритм получения коммиссии за перевод из настроек приложения
                if (amount - commission < 1) {
                    String msg = String.format("Количество купонов передаваемых купонов не может быть " +
                                    "равным или меньше коммисси за операцию! senderId:%d amount:%d commission:%d"
                            , acctSender.getId(), amount, commission);
                    log.warning(msg);
                    throw new Exception(msg);
                }
                operation.setAmount(amount);
                operation.setCommission(commission);
                operation = this.addOperation(operation);
                AcceptCouponOperation acceptOperation = acceptService.addAcceptCouponOperation(operation);
                result = acceptOperation.getCouponOperation();
            } catch (Exception e) {
                log.warning(e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    @Transactional
    public CouponOperation cancelOperationBySender(@NotNull CouponOperation operation) {
        //TODO: Добавить проверку на то что sender не равен нулю
        return this.operationDao.getByIdAndSender_Client_Id(operation.getId(),operation.getSender().getClient().getId());
    }

    @Override
    @Transactional
    public CouponOperation cancelOperation(@NotNull CouponOperation operation) {
        if (operation.getStatus() == CouponOperation.OperationStatus.FREEZE) {
            try {
                accountService.refill(operation.getSender().getId(), new BigDecimal(operation.getAmount()));
            } catch (Exception e) {
                log.warning(e.getMessage());
                e.printStackTrace();
            }
            operation.setStatus(CouponOperation.OperationStatus.CANCELED);
            operation = saveOperation(operation);
        }else operation = null;
        return operation;
    }

    @Override
    @Transactional
    public CouponOperation addConvertOperation(CouponOperation couponOperation) {
        couponOperation.setType(CouponOperation.OperationType.CONVERT);
        return addOperation(couponOperation);
    }

    @Override
    @Transactional
    public CouponOperation completeTransformAndPaymentOperation(Client client, long operation_id, String successCode) {
        CouponOperation operation = this.acceptService.acceptCouponOperation(client, operation_id, successCode);
        if (operation != null && operation.getStatus() == CouponOperation.OperationStatus.FREEZE) {
            try {
                accountService.refill(operation.getReceiver().getId(),
                        new BigDecimal(operation.getAmount() - operation.getCommission()));
            } catch (Exception e) {
                log.warning(e.getMessage());
                e.printStackTrace();
            }
            operation.setStatus(CouponOperation.OperationStatus.COMPLETED);
            operation = saveOperation(operation);
        }
        return operation;
    }

}
