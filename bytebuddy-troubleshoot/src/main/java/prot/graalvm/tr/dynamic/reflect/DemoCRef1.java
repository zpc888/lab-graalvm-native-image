package prot.graalvm.tr.dynamic.reflect;

public class DemoCRef1 {
    private DemoCRef2 ref2 = new DemoCRef2();

    @Override
    public String toString() {
        return String.format("%s-%s ==> instance-ref: %s",
                getClass().getSimpleName(), hashCode(), ref2);
    }
}
