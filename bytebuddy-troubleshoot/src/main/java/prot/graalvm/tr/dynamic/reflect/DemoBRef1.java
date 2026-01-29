package prot.graalvm.tr.dynamic.reflect;

public class DemoBRef1 {
    @Override
    public String toString() {
        return String.format("%s-%s ==> local-ref-new-on-demand: %s",
                getClass().getSimpleName(), hashCode(), new DemoBRef2());
    }
}
