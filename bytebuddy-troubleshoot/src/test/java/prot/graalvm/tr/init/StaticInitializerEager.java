package prot.graalvm.tr.init;

public class StaticInitializerEager {
    static {
        System.out.println("static initialization - Eager");
    }

    static void action() {
        System.out.println("static action method - Eager");
    }
}
