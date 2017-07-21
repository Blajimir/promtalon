package ru.promtalon.service.impl;

import org.springframework.stereotype.Service;
import ru.promtalon.entity.Client;
import ru.promtalon.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public Client createClient(Client client) {
        return null;
    }

    @Override
    public Client regNewClient(Client client) {
        return null;
    }

    @Override
    public Client deleteClient(long id) {
        return null;
    }

    @Override
    public Client updateClient(Client client) {
        return null;
    }

    @Override
    public Client updateClientFields(Client client, Client newDataClient, List<String> fields) {
        return null;
    }

    @Override
    public Client updateClientIgnoreFields(Client client, Client newDataClient, List<String> fields) {
        return null;
    }
}
