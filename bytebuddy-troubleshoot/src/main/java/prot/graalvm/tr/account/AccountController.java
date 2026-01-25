// language: java
package prot.graalvm.tr.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account saved = service.createAccount(account);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<Iterable<Account>> listAllAccounts() {
        Iterable<Account> all = service.listAllAccounts();
        return ResponseEntity.ok(all);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Account> updateType(@PathVariable Long id,
                                              @RequestParam(name = "accountType") String accountType) {
        Optional<Account> saved = service.updateType(id, accountType);
        return saved.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
