/**
 * 
 */
package omc.pedidos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import omc.pedidos.entity.ClienteEntity;
import omc.pedidos.util.JPAUtil;

/**
 * @author ocean
 *
 */
public class ClienteDAOImpl implements ClienteDAO{
	
	private static final Logger LOGGER = Logger.getLogger(ClienteDAOImpl.class);
	
	
	private EntityManager manager;
	
	
	public ClienteDAOImpl(){
		this.manager = JPAUtil.getEntityManager();
	}
	
	public ClienteDAOImpl(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public List<ClienteEntity> listarTodosClientes() {
		List<ClienteEntity> clientes = null;
		try {
			LOGGER.info("Entrou no método listarTodosClientes");
			//Para forçar o JPA ir buscar os dados no banco 
			manager = JPAUtil.getEntityManager();
			
			final Query query = this.manager.createQuery("SELECT c FROM ClienteEntity c", ClienteEntity.class);

			clientes = query.getResultList();

			LOGGER.info("Saiu do método listarTodosClientes com o total de registros = " + clientes.size());
		} catch (Exception e) {
			LOGGER.error("Erro no método listarTodosClientes() >>"  + e.getMessage() );
		}
		return clientes;
	}
	


}
