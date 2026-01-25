// language: java
package prot.graalvm.tr.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import prot.graalvm.tr.client.ClientService;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final prot.graalvm.tr.client.ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<prot.graalvm.tr.client.Client> create(@RequestBody prot.graalvm.tr.client.Client client) {
        prot.graalvm.tr.client.Client saved = service.createClient(client);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<Iterable<prot.graalvm.tr.client.Client>> listAllClients() {
        Iterable<prot.graalvm.tr.client.Client> all = service.listAllClients();
        return ResponseEntity.ok(all);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<prot.graalvm.tr.client.Client> updateType(@PathVariable Long id,
                                                                      @RequestParam(name = "dob") LocalDate dob) {
        Optional<prot.graalvm.tr.client.Client> saved = service.updateDateOfBith(id, dob);
        return saved.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
