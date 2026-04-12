package prot.graalvm.tr.init;

public class StaticInitializerDefault {
    static {
        System.out.println("static initialization - Default");
    }

    static void action() {
        System.out.println("static action method - Default");
    }
}
