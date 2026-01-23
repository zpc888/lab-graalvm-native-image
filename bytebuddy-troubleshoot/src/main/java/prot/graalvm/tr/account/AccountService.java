package prot.graalvm.tr.account;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account createAccount(Account account) {
        return repository.save(account);
    }

    public Optional<Account> getById(Long id) {
        return repository.findById(id);
    }
}