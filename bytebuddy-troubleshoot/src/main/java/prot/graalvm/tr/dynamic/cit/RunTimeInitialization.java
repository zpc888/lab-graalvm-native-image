package prot.graalvm.tr.dynamic.cit;

public class RunTimeInitialization {
    private static final String CITY_NAME = System.getProperty("city");
    private final String city = System.getProperty("city");

    public static String getStaticCity() {
        return CITY_NAME;
    }

    public String getInstanceCity() {
        return city;
    }
}
