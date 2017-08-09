package ru.promtalon.service.impl;

import ru.promtalon.entity.AcceptOperation;
import ru.promtalon.entity.Client;
import ru.promtalon.service.AcceptOperationService;

public class AcceptOperationServiceImpl implements AcceptOperationService {
    @Override
    public AcceptOperation createAcceptOperation(AcceptOperation operation) {
        return null;
    }

    @Override
    public boolean acceptOperation(Client client, String code) {
        return false;
    }

    @Override
    public AcceptOperation deleteAcceptOperation(AcceptOperation operation) {
        return null;
    }
}
