package prot.graalvm.tr.dynamic.conditional1;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import prot.graalvm.tr.dynamic.Conditional1Service;

/*
 ConditionalOnProperty is not supported,
 see https://docs.spring.io/spring-boot/reference/packaging/aot.html
 */
@Service
@ConditionalOnProperty(prefix = "app.conditional", name = "service", havingValue = "AService")
public class ConditionalAService implements Conditional1Service {
    @Override
    public String service() {
        return "A-Service-From-" + conditionId();
    }
}
