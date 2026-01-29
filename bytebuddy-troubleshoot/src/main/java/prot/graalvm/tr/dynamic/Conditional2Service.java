package prot.graalvm.tr.dynamic;

public interface Conditional2Service {
    String service();

    default String conditionId() {
        return "Another-Conditional-2";
    }
}
