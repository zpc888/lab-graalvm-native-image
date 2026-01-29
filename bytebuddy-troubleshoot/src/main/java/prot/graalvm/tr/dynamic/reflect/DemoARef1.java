package prot.graalvm.tr.dynamic.reflect;

public class DemoARef1 {
    private DemoARef2 ref2 = new DemoARef2();

    @Override
    public String toString() {
        return String.format("%s-%s ==> instance-ref: %s",
                getClass().getSimpleName(), hashCode(), ref2);
    }
}
