package prot.graalvm.tr.client;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static prot.graalvm.tr.client.ClientDatabaseConfig.CLIENT_TRANSACTION_MANAGER;

public interface ClientRepository extends CrudRepository<prot.graalvm.tr.client.Client, Long> {
    // additional query methods (if needed) can be declared here

    @Modifying
    @Transactional(CLIENT_TRANSACTION_MANAGER)
    @Query("UPDATE Client a SET a.dob = :dob WHERE a.id = :id")
    int updateClientDateOfBirthById(@Param("id") Long id, @Param("dob") LocalDate dateOfBith);
}