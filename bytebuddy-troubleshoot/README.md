# Troubleshooting notes

## spring boot 3.5.10

### Single DataSource (Default)

When having one data source (default one), it works out-of-box for both running in java and native executable environments.
```properties
spring.datasource.url=jdbc:sqlserver://${DB_HOST:localhost}:1433;databaseName=account;encrypt=false;sendStringParametersAsUnicode=true
spring.datasource.username=sa
spring.datasource.password=verYs3cret
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# HikariCP settings (defaults are fine; tuned for dev)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.pool-name=HikariPool

# JPA / Hibernate (dev convenience - creates schema)
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect

```

### Multiple DataSources


## Spring boot 3.3.13

When having one data source (default one), it works for java environment, native image build is ok, but when running
native executable, it gives the below error:

```text
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'accountController': 
  Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'accountService': 
  Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'accountRepository': 
  Not a managed type: class prot.graalvm.tr.account.Account
```

### Multiple DataSources

Based on [Spring Framework - Ahead of Time Optimizations](https://docs.spring.io/spring-framework/reference/core/aot.html#aot.bestpractices.jpa)
and [graalvm native AOT database issue](https://github.com/DigitalMediageek/aot-database-issue/tree/main), changing
to specifically ensure the entity scanning occurs ahead of time, `PersistenceManagedTypes` is declared and used by 
entity manager factory bean.

It works now even with multiple data sources. It should work for single data sources.

Also for Spring boot 3.5.10, it should work more smoothly.
