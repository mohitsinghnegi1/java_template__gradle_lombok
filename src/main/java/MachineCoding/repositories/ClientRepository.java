package MachineCoding.repositories;

import MachineCoding.clients.Client;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ClientRepository {
    private Map<String, Client> clients = new HashMap<>();
}
