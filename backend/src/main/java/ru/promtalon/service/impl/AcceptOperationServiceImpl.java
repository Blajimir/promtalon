package ru.promtalon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.AcceptOperationDao;
import ru.promtalon.entity.AcceptOperation;
import ru.promtalon.entity.Client;
import ru.promtalon.service.AcceptOperationService;
import ru.promtalon.util.NumberCodeGenerator;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcceptOperationServiceImpl implements AcceptOperationService {

    @Autowired
    private AcceptOperationDao operationDao;

    @Autowired
    private NumberCodeGenerator numberCodeGenerator;

    private AcceptOperation createAcceptOperation(@NotNull Client client, @NotNull AcceptOperation.OperationType type,
                                                  @NotNull AcceptOperation.ContactType contact) {
        AcceptOperation operation = new AcceptOperation();
        operation.setClient(client);
        operation.setType(type);
        operation.setContact(contact);
        operation.setId(0);

        List<AcceptOperation> operations = operationDao.findAllByClient(operation.getClient());
        if (operations.size() > 0) {
            AcceptOperation prevOperation = operations.stream().filter(op -> op.getType() == type)
                    .findFirst().orElse(null);
            if (prevOperation != null) {
                operationDao.delete(prevOperation);
            }
            List<String> token = operations.stream().map(AcceptOperation::getAcceptCode).collect(Collectors.toList());
            operation.setAcceptCode(numberCodeGenerator.tryUniqueCode(10, token));
        } else {
            operation.setAcceptCode(numberCodeGenerator.getToken());
        }
        return operationDao.save(operation);
    }

    @Override
    public AcceptOperation createAcceptPassword(Client client, AcceptOperation.ContactType contact) {
        return createAcceptOperation(client, AcceptOperation.OperationType.RESET_PASSWORD, contact);
    }

    @Override
    public AcceptOperation createAcceptEmail(Client client) {
        return createAcceptOperation(client, AcceptOperation.OperationType.ACCEPT_MAIL,
                AcceptOperation.ContactType.EMAIL);
    }

    @Override
    public AcceptOperation createAcceptPhone(Client client) {
        return createAcceptOperation(client, AcceptOperation.OperationType.ACCEPT_PHONE,
                AcceptOperation.ContactType.PHONE);
    }

    @Override
    public boolean acceptOperation(Client client, AcceptOperation.OperationType type, String code) {
        AcceptOperation operation = operationDao.findByClientAndType(client, type);
        boolean result = operation != null && operation.getAcceptCode().equals(code);
        if (result) {
            operationDao.delete(operation);
        }
        return result;
    }

    @Override
    public void deleteOldAcceptOperation(Date date) {
        List<AcceptOperation> operationList = operationDao.findAllByCreateDateIsLessThanEqual(date);
        if (operationList.size() > 0) {
            operationDao.delete(operationList);
        }
    }
}
