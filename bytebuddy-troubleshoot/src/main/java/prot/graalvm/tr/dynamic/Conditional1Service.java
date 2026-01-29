package prot.graalvm.tr.dynamic;

public interface Conditional1Service {
    String service();

    default String conditionId() {
        return "Conditional-1";
    }
}
