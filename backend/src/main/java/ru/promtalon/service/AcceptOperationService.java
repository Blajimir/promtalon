package ru.promtalon.service;

import ru.promtalon.entity.AcceptOperation;
import ru.promtalon.entity.Client;

public interface AcceptOperationService {
    AcceptOperation createAcceptOperation(AcceptOperation operation);
    boolean acceptOperation(Client client, String code);
    AcceptOperation deleteAcceptOperation(AcceptOperation operation);
}
