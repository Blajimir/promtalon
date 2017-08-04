package ru.promtalon.service;

import ru.promtalon.entity.AcceptCouponOperation;
import ru.promtalon.entity.Client;
import ru.promtalon.entity.CouponOperation;

public interface AcceptCouponOperationService {
    AcceptCouponOperation addAcceptCouponOperation(CouponOperation operation);
    AcceptCouponOperation refreshAcceptCouponOperation(CouponOperation operation);
    void deleteAcceptCouponOperation(CouponOperation operation);
    CouponOperation acceptCouponOperation(Client client, long operation_id, String code);
}
