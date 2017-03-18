package omc.pedidos.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author ocortez
 *
 */
public class JPAUtil {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnitName");
	
	
	public static EntityManager getEntityManager(){
		
		return emf.createEntityManager();
	}
}
