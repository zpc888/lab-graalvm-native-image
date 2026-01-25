package prot.graalvm.tr.account;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "prot.graalvm.tr.account",
        entityManagerFactoryRef = AccountDatabaseConfig.ACCOUNT_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = AccountDatabaseConfig.ACCOUNT_TRANSACTION_MANAGER
)
public class AccountDatabaseConfig {
//    public static final String ACCOUNT_ENTITY_MANAGER_FACTORY = "accountEntityManagerFactory";
//    public static final String ACCOUNT_TRANSACTION_MANAGER = "accountTransactionManager";
    public static final String ACCOUNT_ENTITY_MANAGER_FACTORY = "account-db.em-factory";
    public static final String ACCOUNT_TRANSACTION_MANAGER = "account-db.tx-manager";

    @Bean(name = ACCOUNT_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean accountEntityManagerFactory(DataSource ds) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds);
        em.setPackagesToScan("prot.graalvm.tr.account");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.transaction.jta.platform",
                "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");
        em.setJpaProperties(properties);

        return em;
    }

    @Bean(name = ACCOUNT_TRANSACTION_MANAGER)
    public TransactionManager accountTransactionManager(@Qualifier(ACCOUNT_ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean accountEntityManagerFactory) {
        EntityManagerFactory emf = accountEntityManagerFactory.getObject();
        if (emf != null) {
            return new JpaTransactionManager(emf);
        } else {
            return null;
        }
    }
}
