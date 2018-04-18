package hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "CMS";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }
    public static  boolean setFactory() {
        try {
            if (factory == null) {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void closeDB() {
        if (factory != null) {
            factory.close();
        }
    }
}
