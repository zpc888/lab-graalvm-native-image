package prot.graalvm.tr.account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    // additional query methods (if needed) can be declared here
}