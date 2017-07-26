package ru.promtalon.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.CouponOperationDao;
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
        return null;
    }

    @Override
    public List<CouponOperation> getAllOperationsBySender(CouponAccount sender, CouponOperation couponOperation) {
        return null;
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
    public CouponOperation addTransferOperation(@NotNull CouponAccount sender, @NotNull CouponAccount receiver, long amount) {
        sender = accountService.getAccount(sender.getId());
        receiver = accountService.getAccount(receiver.getId());
        if (sender != null && receiver != null && amount > 0) {
            try {
                accountService.debit(sender.getId(), new BigDecimal(amount));
                CouponOperation operation = new CouponOperation();
                operation.setStatus(CouponOperation.OperationStatus.FREEZE);
                operation.setType(CouponOperation.OperationType.TRANSFER);
                operation.setSender(sender);
                operation.setReceiver(receiver);
                operation.setAmount(amount);
                long commission = 1;//TODO: Написать алгоритм получения коммиссии за перевод из настроек приложения
                if (amount - commission < 1) {
                    String msg = String.format("Количество купонов передаваемых купонов не может быть " +
                            "равным или меньше коммисси за операцию! senderId:%d amount:%d commission:%d"
                            ,sender.getId(),amount,commission);
                    log.warning(msg);
                    throw new Exception(msg);
                }
                operation.setCommission(commission);
                acceptService.addAcceptCouponOperation(operation);
            } catch (Exception e) {
                log.warning(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public CouponOperation cancelOperation(CouponAccount sender) {
        return null;
    }

    @Override
    public CouponOperation cancelOperation(Client sender) {
        return null;
    }

    @Override
    @Transactional
    public CouponOperation cancelOperation(@NotNull CouponOperation operation) {
        operation = getOperation(operation.getId());
        if (operation != null && operation.getStatus() == CouponOperation.OperationStatus.FREEZE) {
            try {
                accountService.refill(operation.getSender().getId(), new BigDecimal(operation.getAmount()));
            } catch (Exception e) {
                log.warning(e.getMessage());
                e.printStackTrace();
            }
            operation.setStatus(CouponOperation.OperationStatus.CANCELED);
            operation = saveOperation(operation);
        }
        return operation;
    }

    @Override
    public CouponOperation completeOperation(CouponAccount sender, String successCode) {
        return null;
    }

    @Override
    public CouponOperation completeOperation(Client sender, String successCode) {
        return null;
    }

    @Override
    @Transactional
    public CouponOperation completeOperation(CouponOperation operation, String successCode) {
        operation = getOperation(operation.getId());
        if (operation != null && operation.getStatus() == CouponOperation.OperationStatus.FREEZE) {
            try {
                accountService.refill(operation.getReceiver().getId(),
                        new BigDecimal(operation.getAmount()-operation.getCommission()));
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
