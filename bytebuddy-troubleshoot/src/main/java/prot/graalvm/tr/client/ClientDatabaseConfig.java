package prot.graalvm.tr.client;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypesScanner;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import prot.graalvm.tr.JpaProperties;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "prot.graalvm.tr.client",
        entityManagerFactoryRef = ClientDatabaseConfig.CLIENT_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = ClientDatabaseConfig.CLIENT_TRANSACTION_MANAGER
)
public class ClientDatabaseConfig {
//    public static final String CLIENT_ENTITY_MANAGER_FACTORY = "clientEntityManagerFactory";
//    public static final String CLIENT_TRANSACTION_MANAGER = "clientTransactionManager";
    public static final String CLIENT_ENTITY_MANAGER_FACTORY = "client-db.em-factory";
    public static final String CLIENT_TRANSACTION_MANAGER = "client-db.tx-manager";

    @Bean("clientDbProps")
    @Primary
    @ConfigurationProperties(prefix = "client.datasource")
    HikariConfig clientDbProps() {
        return new HikariConfig();
    }

    @Bean("clientJpaProps")
    @Primary
    @ConfigurationProperties(prefix = "client")
    JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean("client-db.data-source")
    @Primary
    DataSource graal1DataSource(@Qualifier("clientDbProps") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean("clientEMFB")
    @Primary
    EntityManagerFactoryBuilder graal1EMFB(ObjectProvider<PersistenceUnitManager> persistenceUnitManager,
                                           @Qualifier("clientJpaProps") JpaProperties jpaProperties) {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties.get(), persistenceUnitManager.getIfAvailable());
    }

    @Bean("clientPMT")
    @Primary
    PersistenceManagedTypes clientPMT(ResourceLoader resourceLoader) {
        return new PersistenceManagedTypesScanner(resourceLoader)
                .scan("prot.graalvm.tr.client");
    }

    @Bean(name = CLIENT_ENTITY_MANAGER_FACTORY)
    @Primary
    LocalContainerEntityManagerFactoryBean clientEntityManagerFactory(
            @Qualifier("client-db.data-source") DataSource ds
            , @Qualifier("clientEMFB") EntityManagerFactoryBuilder emfBuilder
            , @Qualifier("clientPMT") PersistenceManagedTypes persistenceManagedTypes
            ) {
        return emfBuilder.dataSource(ds)
                .packages(Client.class)
                .managedTypes(persistenceManagedTypes)
                .build();
        /*
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds);
        em.setPackagesToScan("prot.graalvm.tr.client");
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
         */
    }

    @Bean(name = CLIENT_TRANSACTION_MANAGER)
    @Primary
    public TransactionManager clientTransactionManager(@Qualifier(CLIENT_ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean clientEntityManagerFactory) {
        EntityManagerFactory emf = clientEntityManagerFactory.getObject();
        if (emf != null) {
            return new JpaTransactionManager(emf);
        } else {
            throw new NullPointerException("client entity-manager-factory cannot be null");
            // return null;
        }
    }
}
