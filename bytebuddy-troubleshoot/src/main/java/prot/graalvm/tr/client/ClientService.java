package prot.graalvm.tr.client;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client createClient(Client client) {
        return repository.save(client);
    }

    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

    public Optional<Client> updateDateOfBith(Long id, LocalDate dateOfBith) {
        int ret = repository.updateClientDateOfBirthById(id, dateOfBith);
        if (ret == 1) {
            return repository.findById(id);
        }
        return Optional.empty();
    }

    public Iterable<Client> listAllClients() {
        return repository.findAll();
    }
}