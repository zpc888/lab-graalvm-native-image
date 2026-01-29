package prot.graalvm.tr.dynamic.reflect;

public class DemoARef2 {
    @Override
    public String toString() {
        return String.format("%s-%s", getClass().getSimpleName(), hashCode());
    }
}
