package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.AcceptCouponOperationDao;
import ru.promtalon.entity.AcceptCouponOperation;
import ru.promtalon.entity.CouponOperation;
import ru.promtalon.service.AcceptCouponOperationService;
import ru.promtalon.service.CouponOperationService;
import ru.promtalon.util.NumberCodeGenerator;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class AcceptCouponOperationServiceImpl implements AcceptCouponOperationService {

    @Autowired
    private AcceptCouponOperationDao acceptDao;
    @Autowired
    private CouponOperationService operationService;
    @Autowired
    private NumberCodeGenerator numberCodeGenerator;
    @Autowired
    private Md5PasswordEncoder md5PasswordEncoder;
    @Override
    public AcceptCouponOperation addAcceptCouponOperation(@NotNull CouponOperation operation) {
        AcceptCouponOperation result = null;
        operation = operationService.getOperation(operation.getId());
        if(operation!=null){
            result = new AcceptCouponOperation();
            result.setCouponOperation(operation);
            String code = numberCodeGenerator.getToken();
            result.setAcceptCode(code);
            acceptDao.save(result);
        }
        return result;
    }

    @Override
    public AcceptCouponOperation refreshAcceptCouponOperation(CouponOperation operation) {
        return null;
    }

    @Override
    public AcceptCouponOperation deleteAcceptCouponOperation(CouponOperation operation) {
        return null;
    }

    @Override
    public boolean acceptCouponOperation(CouponOperation operation, String code) {
        return false;
    }

    private String tryUniqCodeForSender(CouponOperation operation){
        String code = numberCodeGenerator.getToken();
        List<AcceptCouponOperation> acceptList
        for (int i = 0; i < 10; i++) {

        }
        return code;
    }
}
