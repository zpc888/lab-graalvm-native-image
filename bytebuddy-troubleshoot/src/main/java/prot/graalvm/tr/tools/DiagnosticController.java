package prot.graalvm.tr.tools;

import jakarta.persistence.EntityManagerFactory;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/diagnostic")
public class DiagnosticController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/emf-info")
    public Map<String, Object> getEmfCount() {
        Map<String, Object> result = new LinkedHashMap<>();

        String[] emfBeans = context.getBeanNamesForType(EntityManagerFactory.class);
        result.put("emf.count", emfBeans.length);
        result.put("emf.beanNames", Arrays.asList(emfBeans));

        // Get details for each
        List<Map<String, String>> details = new ArrayList<>();
        for (String beanName : emfBeans) {
            Map<String, String> detail = new LinkedHashMap<>();
            detail.put("emf.beanName", beanName);

            EntityManagerFactory emf = context.getBean(beanName, EntityManagerFactory.class);
            detail.put("emf.className", emf.getClass().getName());
            detail.put("emf.persistenceUnitName",
                    String.valueOf(emf.getProperties().get("hibernate.ejb.persistenceUnitName")));

            try {
                LocalContainerEntityManagerFactoryBean factoryBean =
                        (LocalContainerEntityManagerFactoryBean) context.getBean("&" + beanName);
                detail.put("emf.my.LocalContainerEntityManagerFactoryBean.is", "yes");
                detail.put("emf.my.LocalContainerEntityManagerFactoryBean.DataSource", factoryBean.getDataSource().toString());
                detail.put("emf.my.LocalContainerEntityManagerFactoryBean.PersistenceUnit", factoryBean.getPersistenceUnitName());
                System.out.println("  ZPC: Factory Bean exists: Yes");
                System.out.println("  ZPC: DataSource: " + factoryBean.getDataSource());
                System.out.println("  ZPC: Packages to scan: " + factoryBean.getPersistenceUnitName());
            } catch (Exception e) {
                System.out.println("  ZPC: Factory Bean exists: No (or not accessible)");
            }

            details.add(detail);
        }
        result.put("emf.details", details);

        String[] builderNames = context.getBeanNamesForType(
                org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.class);
        System.out.println("\nZPC: Number of LocalContainerEntityManagerFactoryBean beans: " + builderNames.length);
        System.out.println("\nZPC: Names of LocalContainerEntityManagerFactoryBean beans: " + Strings.join(Arrays.asList(builderNames), ','));
        result.put("LocalContainerEntityManagerFactoryBean.count", builderNames.length);
        result.put("LocalContainerEntityManagerFactoryBean.name-list", Strings.join(Arrays.asList(builderNames), ','));

        return result;
    }
}