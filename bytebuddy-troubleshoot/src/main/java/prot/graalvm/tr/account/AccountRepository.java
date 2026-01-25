package prot.graalvm.tr.account;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import static prot.graalvm.tr.account.AccountDatabaseConfig.ACCOUNT_TRANSACTION_MANAGER;

public interface AccountRepository extends CrudRepository<Account, Long> {
    // additional query methods (if needed) can be declared here

    @Modifying
    @Transactional(ACCOUNT_TRANSACTION_MANAGER)
    @Query("UPDATE Account a SET a.type = :accountType WHERE a.id = :id")
    int updateAccountTypeById(@Param("id") Long id, @Param("accountType") String accountType);
}