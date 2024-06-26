package MachineCoding.repositories;

import MachineCoding.clients.Client;
import MachineCoding.exceptions.BadRequestException;
import MachineCoding.interfaces.IClientRepository;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ClientRepository implements IClientRepository {

    private static ClientRepository INSTANCE;


    private ClientRepository(){}

    public static synchronized ClientRepository  getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new ClientRepository();
        }
        return INSTANCE;
    }


    private Map<String, Client> clients = new HashMap<>();

    @Override
    public Client addClient(Client client) {
        return clients.put(client.getName(), client);
    }

    @Override
    public Client removeClient(String clientName) {
        if (!hasClient(clientName)) {
            throw new BadRequestException(100,"Client doesn't exist");
        }
        return clients.remove(clientName);
    }
    @Override
    public boolean hasClient(String clientName) {
        return clients.containsKey(clientName);
    }

    @Override
    public Client getClient(String clientName) {
        return clients.get(clientName);
    }
}
