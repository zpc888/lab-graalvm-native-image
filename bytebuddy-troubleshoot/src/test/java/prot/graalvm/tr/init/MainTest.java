package prot.graalvm.tr.init;

public class MainTest {
    public static void main(String[] args) throws Exception {

        System.out.println("=============== class for name default =============");
        Class<?> aClass = Class.forName("prot.graalvm.tr.init.StaticInitializerDefault");
        System.out.println("  1.after Class.forName");
        aClass.getDeclaredMethod("action", new Class[0]).invoke(null, new Object[0]);
        System.out.println("  2.action method");
        aClass.getDeclaredMethod("action", new Class[0]).invoke(null, new Object[0]);

        ClassLoader classLoader = MainTest.class.getClassLoader();
        System.out.println("=============== class for name Lazy =============");
        Class<?> bClass = Class.forName("prot.graalvm.tr.init.StaticInitializerLazy", false, classLoader);
        System.out.println("  1.after Class.forName");
        bClass.getDeclaredMethod("action", new Class[0]).invoke(null, new Object[0]);
        System.out.println("  2.action method");
        bClass.getDeclaredMethod("action", new Class[0]).invoke(null, new Object[0]);

        System.out.println("=============== class for name Eager =============");
        Class<?> cClass = Class.forName("prot.graalvm.tr.init.StaticInitializerEager", true, classLoader);
        System.out.println("  1.after Class.forName");
        cClass.getDeclaredMethod("action", new Class[0]).invoke(null, new Object[0]);
        System.out.println("  2.action method");
        cClass.getDeclaredMethod("action", new Class[0]).invoke(null, new Object[0]);

    }

}
