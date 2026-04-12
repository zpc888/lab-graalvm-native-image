package prot.graalvm.tr.init;

public class StaticInitializerLazy {
    static {
        System.out.println("static initialization - Lazy");
    }

    static void action() {
        System.out.println("static action method - Lazy");
    }
}
