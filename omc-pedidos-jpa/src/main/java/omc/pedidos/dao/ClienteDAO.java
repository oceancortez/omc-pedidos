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
public class ClienteDAO extends GenericDAO<Long, ClienteEntity>{
	
	public ClienteDAO(EntityManager entityManager) {
		super(entityManager);
		
	}


	private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class);
	
	public List<ClienteEntity> listarTodosClientes() {
		List<ClienteEntity> clientes = null;
		try {
			LOGGER.info("Entrou no método listarTodosClientes");
			
			final Query query = this.getEntityManager().createQuery("SELECT c FROM ClienteEntity c", ClienteEntity.class);

			clientes = query.getResultList();

			LOGGER.info("Saiu do método listarTodosClientes com o total de registros = " + clientes.size());
		} catch (Exception e) {
			LOGGER.error("Erro no método listarTodosClientes() >>"  + e.getMessage() );
		}
		return clientes;
	}
	

	public ClienteEntity salvar(ClienteEntity cliente) {

		try {
			LOGGER.info("Entrou no método salvar");			
			this.getEntityManager().getTransaction().begin();
			cliente = this.getEntityManager().merge(cliente);
			this.getEntityManager().getTransaction().commit();
			this.getEntityManager().close();	

			LOGGER.info("Saiu do método salvar  cliente.getCodigo = " + cliente.getCodigo());
		} catch (Exception e) {
			LOGGER.error("Erro no método salvar() >>"  + e.getMessage());
		}
		
		return cliente;
	}
	


}
