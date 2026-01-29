package prot.graalvm.tr.dynamic.conditional2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prot.graalvm.tr.dynamic.Conditional2Service;

@Configuration
public class AnotherConditionalConfiguration {
    @Bean
    public Conditional2Service conditional2Service(@Value("${app.conditional.service:}") String whichOne) {
        if ("AService".equals(whichOne)) {
            return new AnotherConditionalAService();
        } else {
            return new AnotherConditionalBService();
        }
    }
}
