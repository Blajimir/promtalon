package ru.promtalon.service;

import ru.promtalon.entity.AcceptOperation;
import ru.promtalon.entity.Client;

import javax.validation.constraints.NotNull;
import java.util.Date;

public interface AcceptOperationService {
    AcceptOperation createAcceptPassword(@NotNull Client client, @NotNull AcceptOperation.ContactType contact);
    AcceptOperation createAcceptEmail(@NotNull Client client);
    AcceptOperation createAcceptPhone(@NotNull Client client);
    boolean acceptOperation(Client client, AcceptOperation.OperationType type, String code);
    void deleteOldAcceptOperation(Date date);
}
