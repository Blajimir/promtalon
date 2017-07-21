package ru.promtalon.service;

import ru.promtalon.entity.Client;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);
    Client regNewClient(Client client);
    Client deleteClient(long id);
    Client updateClient(Client client);
    Client updateClientFields(Client client, Client  newDataClient, List<String> fields);
    Client updateClientIgnoreFields(Client client, Client  newDataClient, List<String> fields);
}
