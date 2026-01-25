package prot.graalvm.tr.tools;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class DebugInterceptor implements StatementInspector {
    @Override
    public String inspect(String sql) {
        // Check if lazy loading is triggered
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            if (element.getClassName().contains("BytecodeProviderImpl")) {
                System.err.println("BYTECODE GENERATION ATTEMPTED: " + element);
            }
        }
        return sql;
    }
}
