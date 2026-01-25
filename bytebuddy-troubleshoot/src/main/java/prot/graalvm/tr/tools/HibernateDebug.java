package prot.graalvm.tr.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class HibernateDebug implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(HibernateDebug.class);

    @Override
    public void afterPropertiesSet() {
        log.info("Hibernate version: {}", org.hibernate.Version.getVersionString());

        // Check bytecode provider
        String provider = System.getProperty("hibernate.bytecode.provider");
        log.info("Bytecode provider system property: {}", provider);
    }
}