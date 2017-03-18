/**
 * 
 */
package omc.pedidos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import omc.pedidos.entity.PedidoEntity;
import omc.pedidos.util.JPAUtil;

/**
 * @author ocean
 *
 */
public class PedidoDAOImpl implements PedidoDAO{
	
	private static final Logger LOGGER = Logger.getLogger(PedidoDAOImpl.class);
	
	
	private EntityManager manager;
	
	
	public PedidoDAOImpl(){
		this.manager = JPAUtil.getEntityManager();
	}
	
	public PedidoDAOImpl(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public List<PedidoEntity> listarTodosPedidos() {
		List<PedidoEntity> pedidos = null;
		try {
			LOGGER.info("Entrou no método listarTodosPedidos");
			//Para forçar o JPA ir buscar os dados no banco 
			manager = JPAUtil.getEntityManager();
			
			final Query query = this.manager.createQuery("SELECT p FROM PedidoEntity p", PedidoEntity.class);

			pedidos = query.getResultList();

			LOGGER.info("Saiu do método listarTodosPedidos com o total de registros = " + pedidos.size());
		} catch (Exception e) {
			LOGGER.error("Erro no método listarTodosPedidos() >>"  + e.getMessage() );
		}
		return pedidos;
	}
	


}
