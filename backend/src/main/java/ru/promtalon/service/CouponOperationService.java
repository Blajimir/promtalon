package ru.promtalon.service;

import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponAccount;
import ru.promtalon.entity.CouponOperation;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
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

    List<CouponOperation> getAllOperationsBySender(CouponAccount sender, CouponOperation.OperationType type);

    List<CouponOperation> getAllOperationsByReceiver(CouponAccount receiver, CouponOperation couponOperation);

    List<CouponOperation> getAllSenderOperationsByStatus(Client sender, CouponOperation.OperationStatus status);

    List<CouponOperation> getAllSenderOperationsByType(Client sender, CouponOperation.OperationType type);

    List<CouponOperation> getAllSenderOperationsByStatusAndType(Client sender, CouponOperation.OperationStatus status);

    CouponOperation addTransferOperation(CouponAccount sender, CouponAccount receiver, long amount);

    @Transactional
    CouponOperation cancelOperationBySender(@NotNull CouponOperation operation);

    CouponOperation cancelOperation(CouponOperation operation);

    CouponOperation addConvertOperation(CouponOperation couponOperation);

    CouponOperation completeTransformAndPaymentOperation(Client client, long operation_id,String successCode);

}
