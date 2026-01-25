package prot.graalvm.tr;

import java.util.Map;

public class JpaProperties {

    /**
     * Store jpa properties here
     */
    private Map<String, String> jpa;

    public void setJpa(Map<String, String> props) {
        this.jpa = props;
    }

    /**
     * Explicit getter for a nicer name
     * @return the properties
     */
    public Map<String, String> get() {
        return jpa;
    }
}