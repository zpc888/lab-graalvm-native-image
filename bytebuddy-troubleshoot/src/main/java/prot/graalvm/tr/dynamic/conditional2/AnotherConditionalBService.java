package prot.graalvm.tr.dynamic.conditional2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import prot.graalvm.tr.dynamic.Conditional2Service;

@Service
@ConditionalOnProperty(prefix = "app.conditional", name = "service", havingValue = "BService", matchIfMissing = true)
public class AnotherConditionalBService implements Conditional2Service {
    @Override
    public String service() {
        return "B-Service-From-" + conditionId();
    }
}
