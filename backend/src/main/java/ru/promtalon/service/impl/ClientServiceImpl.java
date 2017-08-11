package ru.promtalon.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.ClientDao;
import ru.promtalon.entity.AcceptOperation;
import ru.promtalon.entity.Client;
import ru.promtalon.service.*;
import ru.promtalon.util.DataAccessUtil;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Log
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;
    @Autowired
    private UserService userService;
    @Autowired
    private AcceptOperationService operationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;
    @Autowired
    private SmsService smsService;

    //TODO: Возможно не стоит разрешать создавать пользователей даже с правада ADMIN и оставить CRUD метода только для тестов
    @Override
    public Client createClient(Client client) {
        if (client != null) {
            client.setId(0);
        }
        return clientDao.save(client);
    }

    //TODO: Продумать настрйоки транзакции
    @Override
    @Transactional
    public Client regNewClient(Client client) {
        if (client.getUser() != null) {
            client.setUser(userService.regNewUser(client.getUser()));
        } else {
            return null;
        }
        client.getContact().setEmailAccept(false);
        client.getContact().setPhoneAccept(false);
        return createClient(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientDao.findAll();
    }

    @Override
    public Client getClient(long id) {
        if (id < 1) return null;
        return clientDao.findOne(id);
    }

    @Override
    public Client getClientByUsername(String username) {
        return null;
    }

    @Override
    public Client deleteClient(long id) {
        Client client = clientDao.findOne(id);
        if (client != null) {
            clientDao.delete(client);
        }
        return client;
    }

    @Override
    @Transactional
    public Client deactivateClient(long id) {
        if (id == 0) return null;
        Client client = clientDao.getClientByIdAndUserNotNull(id);
        if (client != null) deactivateClient(client);
        return client;
    }

    //TODO: Что делать со счетом пользователя?!
    @Override
    @Transactional
    public Client deactivateClient(@NotNull Client client) {
        if (client.getId() == 0 || client.getUser() == null || client.getUser().getId() == 0) return null;
        long id = client.getUser().getId();
        String username = client.getUser().getUsername();
        client.setUser(null);
        userService.deleteUser(id);
        clientDao.save(client);
        log.info(String.format("User: %s successful delete and client profile (id: %d) deactivate",
                username, client.getId()));
        return client;
    }

    @Override
    @Transactional
    public Client blockClient(long id) {
        if (id == 0) return null;
        Client client = clientDao.getActiveClient(id);
        if (client != null) {
            client.getUser().setEnabled(false);
            userService.updateUser(client.getUser());
        }
        return client;
    }

    @Override
    public Client unblockClient(long id) {
        if (id == 0) return null;
        Client client = clientDao.getDeactiveClient(id);
        if (client != null) {
            client.getUser().setEnabled(true);
            userService.updateUser(client.getUser());
        }
        return client;
    }

    @Override
    public Client updateClient(Client client) {
        if (client.getId() > 0) {
            return clientDao.save(client);
        }
        return null;
    }

    @Override
    @Transactional
    public void resetOperation(Client client, AcceptOperation.OperationType type, AcceptOperation.ContactType contactType){
        AcceptOperation operation;
        switch (type) {
            case ACCEPT_MAIL:
                operation = operationService.createAcceptEmail(client);
                mailService.sendMail(client,"Код подтверждение",
                        String.format("Код подтверждения: %s",operation.getAcceptCode()));
                break;
            case ACCEPT_PHONE:
                operation = operationService.createAcceptPhone(client);
                smsService.sendSms(client, String.format("Код подтверждения: %s",operation.getAcceptCode()));
                break;
            case RESET_PASSWORD:
                operation = operationService.createAcceptPassword(client, contactType);
                if(contactType == AcceptOperation.ContactType.EMAIL){
                    mailService.sendMail(client,"Код подтверждение смены пароля",
                            String.format("Код подтверждения: %s",operation.getAcceptCode()));
                }else if(contactType == AcceptOperation.ContactType.PHONE){
                    smsService.sendSms(client, String.format("Код подтверждения смены пароля: %s",
                            operation.getAcceptCode()));
                }

        }
    }

    @Override
    @Transactional
    public boolean acceptUpdate(Client client, AcceptOperation.OperationType type, String code, String value) {
        boolean result = operationService.acceptOperation(client, type, code);
        if (result) {
            switch (type) {
                case ACCEPT_MAIL:
                    updateMail(client, value);
                    break;
                case ACCEPT_PHONE:
                    updatePhone(client, value);
                    break;
                case RESET_PASSWORD:
                    updatePassword(client, value);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Client updateMail(Client client, String mail) {
        Client result = null;
        if (!client.getContact().getEmail().equals(mail)) {
            client.getContact().setEmail(mail);
            result = clientDao.save(client);
        }
        return result;
    }

    @Override
    @Transactional
    public Client updatePhone(Client client, String phone) {
        Client result = null;
        if (!client.getContact().getPhone().equals(phone)) {
            client.getContact().setPhone(phone);
            result = clientDao.save(client);
        }
        return result;
    }

    @Override
    @Transactional
    public Client updatePassword(Client client, String newPassword) {
        Client result = null;
        if (!passwordEncoder.matches(newPassword, client.getUser().getPassword())) {
            client.getUser().setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(client.getUser());
            result = client;
        }
        return result;
    }

    @Override
    public Client updateClientFields(Client client, Client newDataClient, List<String> fields) {
        try {
            DataAccessUtil.updateFields(client, newDataClient, fields);
            clientDao.save(client);
        } catch (IllegalAccessException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client updateClientIgnoreFields(Client client, Client newDataClient, List<String> fields) {
        try {
            DataAccessUtil.updateIgnoreFields(client, newDataClient, fields);
            clientDao.save(client);
        } catch (IllegalAccessException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
        return client;
    }
}
