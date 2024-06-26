package MachineCoding.interfaces;

import MachineCoding.clients.Client;

import java.util.Map;

public interface IClientRepository {
    Client addClient(Client client);

    Client removeClient(String clientName);

    boolean hasClient(String clientName);

    Map<String, Client> getClients();

    void setClients(Map<String, Client> clients);
    Client getClient(String clientName);
}
