package prot.graalvm.tr.dynamic.reflect;

import prot.graalvm.tr.dynamic.ReflectDemo;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DemoCImpl implements ReflectDemo {
    private DemoCRef1 ref1 = new DemoCRef1();

    @Override
    public void invoke(List<String> collector) {
        collector.add(String.format(">> %s is called", getClass().getSimpleName()));
        collector.add("load resource: " + loadResource("/reflect-C-resource.txt"));
        collector.add("local class ref: " + ref1);
        collector.add("<< return");
    }

    public String loadResource(String resource) {
        try (InputStream is = getClass().getResourceAsStream(resource)) {
            if (is == null) {
                return String.format("Resource not found: %s", resource);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            return String.format("Fail to load resource: %s, exception stack: %s",
                    resource, sw.toString());
        }
    }
}