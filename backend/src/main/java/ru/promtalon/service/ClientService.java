package ru.promtalon.service;

import ru.promtalon.entity.Client;

public interface ClientService {
    Client createClient(Client client);
    Client deleteClient(long id);
    Client updateClient(Client client);
}
