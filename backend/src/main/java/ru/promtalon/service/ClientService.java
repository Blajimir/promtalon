package ru.promtalon.service;

import ru.promtalon.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    Client getClient(long id);
    Client getClientByUsername(String username);
    Client createClient(Client client);
    Client regNewClient(Client client);
    Client deleteClient(long id);
    Client deactivateClient(long id);
    Client deactivateClient(Client client);
    Client blockClient(long id);
    Client unblockClient(long id);
    Client updateClient(Client client);
    Client updateClientFields(Client client, Client  newDataClient, List<String> fields);
    Client updateClientIgnoreFields(Client client, Client  newDataClient, List<String> fields);
}
