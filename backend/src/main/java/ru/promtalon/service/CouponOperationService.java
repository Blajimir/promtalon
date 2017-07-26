package ru.promtalon.service;

import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponAccount;
import ru.promtalon.entity.CouponOperation;

import java.util.List;

public interface CouponOperationService {
    //TODO: реализовать методы для управления операциями с купонами
    CouponOperation addOperation(CouponOperation couponOperation);

    CouponOperation saveOperation(CouponOperation couponOperation);

    CouponOperation getOperation(long id);

    CouponOperation deleteOperation(CouponOperation couponOperation);

    CouponOperation deleteOperation(long id);

    CouponOperation changeStatusOperation(CouponOperation couponOperation, CouponOperation.OperationStatus status);

    List<CouponOperation> getAllOperations();

    List<CouponOperation> getAllOperationsBySender(CouponAccount sender, CouponOperation couponOperation);

    List<CouponOperation> getAllOperationsByReceiver(CouponAccount receiver, CouponOperation couponOperation);

    List<CouponOperation> getAllSenderOperationsByStatus(Client sender, CouponOperation.OperationStatus status);

    List<CouponOperation> getAllSenderOperationsByType(Client sender, CouponOperation.OperationType type);

    List<CouponOperation> getAllSenderOperationsByStatusAndType(Client sender, CouponOperation.OperationStatus status);

    CouponOperation addTransferOperation(CouponAccount sender, CouponAccount receiver, long amount);

    CouponOperation cancelOperation(CouponOperation operation);

    CouponOperation cancelOperation(CouponAccount sender);

    CouponOperation cancelOperation(Client sender);

    CouponOperation completeOperation(CouponOperation operation, String successCode);

    CouponOperation completeOperation(CouponAccount sender, String successCode);

    CouponOperation completeOperation(Client sender, String successCode);

}
