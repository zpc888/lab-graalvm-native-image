package prot.graalvm.tr.dynamic;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prot.graalvm.tr.dynamic.cit.BuildTimeInitialization;
import prot.graalvm.tr.dynamic.cit.RunTimeInitialization;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {
    private final Conditional1Service conditional1Service;
    private final Conditional2Service conditional2Service;
    private final String conditionalServiceSelector;

    public DynamicController(@Autowired Conditional1Service conditionalService,
                             @Autowired Conditional2Service conditional2Service,
                             @Value("${app.conditional.service:}") String whichOne) {
        this.conditional1Service = conditionalService;
        this.conditional2Service = conditional2Service;
        this.conditionalServiceSelector = whichOne;
    }

    @GetMapping("/reflect-demo")
    public ResponseEntity<List<String>> reflect(@RequestParam(name = "fqn", required = false) String clzName) {
        boolean ok = true;
        List<String> ret = new ArrayList<>(8);
        if (Strings.isBlank(clzName)) {
            ok = false;
            ret.add("Error: Query Parameter 'fqn=full-qualified-classname' is needed");
        } else {
            ret.add("try to load class: '" + clzName + "'");
            Class<?> clz = null;
            try {
                clz = Class.forName(clzName);
            } catch (Exception ex) {
                ok = false;
                ret.add(String.format("Error: fail to load class: '%s' with %s", clzName, getStackTrace(ex)));
            }
            if (ok) {
                ret.add("class is dynamically loaded, try to instantiate");
                ReflectDemo demo = null;
                try {
                    Object obj = clz.getConstructor(new Class[0]).newInstance(new Object[0]);
                    ret.add("an object is created, try to cast");
                    demo = (ReflectDemo) obj;
                    ret.add("cast to ReflectDemo type successfully, try to invoke it");
                } catch (Exception ex) {
                    ok = false;
                    ret.add(String.format("Error: fail to instantiate/cast a class object: '%s' with %s", clzName, getStackTrace(ex)));
                }
                if (ok) {
                    try {
                        demo.invoke(ret);
                        ret.add("ok: invoked");
                    } catch (Exception ex) {
                        ok = false;
                        ret.add(String.format("Error: fail to invoke the instance: %s with %s", demo, getStackTrace(ex)));
                    }
                }
            }
        }

        return ok ? ResponseEntity.ok(ret) : ResponseEntity.badRequest().body(ret);
    }


    @GetMapping("/conditional-service")
    public ResponseEntity<Map<String, String>> conditionalService() {
        Map<String, String> ret = new LinkedHashMap<>();
        ret.put("prop-value", conditionalServiceSelector);
        ret.put("conditional-1-bean-result", conditional1Service.service());
        ret.put("another-conditional-2-bean-result", conditional2Service.service());
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/class-initialization-time")
    public ResponseEntity<Map<String, String>> buildVsRuntimeInitialization() {
        Map<String, String> ret = new LinkedHashMap<>(4);
        ret.put("city-from-build-time-initialization-static-field", BuildTimeInitialization.getStaticCity());
        ret.put("city-from-build-time-initialization-instance-field", new BuildTimeInitialization().getInstanceCity());
        ret.put("city-from-run-time-initialization-static-field", RunTimeInitialization.getStaticCity());
        ret.put("city-from-run-time-initialization-instance-field", new RunTimeInitialization().getInstanceCity());
        return ResponseEntity.ok(ret);
    }

    private String getStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
