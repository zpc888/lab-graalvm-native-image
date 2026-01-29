package prot.graalvm.tr.dynamic.conditional1;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import prot.graalvm.tr.dynamic.Conditional1Service;

@Service
@ConditionalOnProperty(prefix = "app.conditional", name = "service", havingValue = "BService", matchIfMissing = true)
public class ConditionalBService implements Conditional1Service {
    @Override
    public String service() {
        return "B-Service-From-" + conditionId();
    }
}
