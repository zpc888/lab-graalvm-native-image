package prot.graalvm.tr.dynamic.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import prot.graalvm.tr.dynamic.ConditionalService;

@Service
@ConditionalOnProperty(prefix = "app.conditional", name = "service", havingValue = "AService")
public class ConditionalAService implements ConditionalService {
    @Override
    public String service() {
        return "A-Service";
    }
}
