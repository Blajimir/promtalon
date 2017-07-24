package ru.promtalon.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.promtalon.dao.ClientDao;
import ru.promtalon.entity.Client;
import ru.promtalon.service.ClientService;
import ru.promtalon.service.UserService;
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
    //TODO: Возможно не стоит разрешать создавать пользователей даже с правада ADMIN и оставить CRUD метода только для тестов
    @Override
    public Client createClient(Client client) {
        if (client!=null) {
            client.setId(0);
        }
        return clientDao.save(client);
    }
    //TODO: Продумать настрйоки транзакции
    @Override
    @Transactional
    public Client regNewClient(Client client) {
        if (client.getUser()!=null) {
            client.setUser(userService.regNewUser(client.getUser()));
        }else{
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
        if (id<1) return null;
        return clientDao.getOne(id);
    }

    @Override
    public Client getClientByUsername(String username) {
        return null;
    }

    @Override
    public Client deleteClient(long id) {
        Client client = clientDao.findOne(id);
        if (client!=null) {
            clientDao.delete(id);
        }

        return client;
    }

    @Override
    @Transactional
    public Client deactivateClient(long id) {
        if (id==0) return null;
        Client client = clientDao.getClientByIdAndUserNotNull(id);
        if(client!=null) deactivateClient(client);
        return client;
    }
    //TODO: Что делать со счетом пользователя?!
    @Override
    @Transactional
    public Client deactivateClient(@NotNull Client client) {
        if(client.getId()==0||client.getUser()==null||client.getUser().getId()==0) return null;
        long id = client.getUser().getId();
        String username = client.getUser().getUsername();
        client.setUser(null);
        userService.deleteUser(id);
        clientDao.save(client);
        log.info(String.format("User: %s successful delete and client profile (id: %d) deactivate",
                username,client.getId()));
        return client;
    }

    @Override
    @Transactional
    public Client blockClient(long id) {
        if (id==0) return null;
        Client client = clientDao.getActiveClient(id);
        if(client!=null){
            client.getUser().setEnabled(false);
            userService.updateUser(client.getUser());
        }
        return client;
    }

    @Override
    public Client unblockClient(long id) {
        if (id==0) return null;
        Client client = clientDao.getDeactiveClient(id);
        if(client!=null){
            client.getUser().setEnabled(true);
            userService.updateUser(client.getUser());
        }
        return client;
    }

    @Override
    public Client updateClient(Client client) {
        if(client.getId()>0){
           return clientDao.save(client);
        }
        return null;
    }

    @Override
    public Client updateClientFields(Client client, Client newDataClient, List<String> fields) {
        try {
            DataAccessUtil.updateFields(client,newDataClient,fields);
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
            DataAccessUtil.updateIgnoreFields(client,newDataClient,fields);
            clientDao.save(client);
        } catch (IllegalAccessException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
        return client;
    }
}
