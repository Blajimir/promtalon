package ru.promtalon.service;

import ru.promtalon.entity.AcceptCouponOperation;
import ru.promtalon.entity.CouponOperation;

public interface AcceptCouponOperationService {
    AcceptCouponOperation addAcceptCouponOperation(CouponOperation operation);
    AcceptCouponOperation refreshAcceptCouponOperation(CouponOperation operation);
    AcceptCouponOperation deleteAcceptCouponOperation(CouponOperation operation);
    boolean acceptCouponOperation(CouponOperation operation, String code);
}
