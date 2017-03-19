/**
 * 
 */
package omc.pedidos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import omc.pedidos.entity.PedidoEntity;

/**
 * @author ocean
 *
 */
public class PedidoDAO extends GenericDAO<Long, PedidoEntity>{
	
	private static final Logger LOGGER = Logger.getLogger(PedidoDAO.class);
	
	public PedidoDAO(EntityManager entityManager) {
		super(entityManager);
	}

	

	public List<PedidoEntity> listarTodosPedidos() {
		List<PedidoEntity> pedidos = null;
		try {
			LOGGER.info("Entrou no método listarTodosPedidos");
			//Para forçar o JPA ir buscar os dados no banco 
			//manager = JPAUtil.getEntityManager();
			
			final Query query = this.getEntityManager().createQuery("SELECT p FROM PedidoEntity p", PedidoEntity.class);

			pedidos = query.getResultList();

			LOGGER.info("Saiu do método listarTodosPedidos com o total de registros = " + pedidos.size());
		} catch (Exception e) {
			LOGGER.error("Erro no método listarTodosPedidos() >>"  + e.getMessage() );
		}
		return pedidos;
	} 
	
	
	public boolean salvarPedido(PedidoEntity pedidoEntity){
		boolean retorno = false;
		
		try {
			LOGGER.info("Entou do método salvarPedido");
			this.getEntityManager().getTransaction().begin();
			pedidoEntity = this.getEntityManager().merge(pedidoEntity);
			this.getEntityManager().getTransaction().commit();
			this.getEntityManager().close();
			retorno = true;
			LOGGER.info("Saiu do método pedidoEntity >> pedidoEntity.getCodigo() =".concat(pedidoEntity.getCodigo().toString()));
		} catch (Exception e) {
			LOGGER.error("Erro no método salvarPedido() >>"  + e.getMessage());
		}
		
		return retorno;
	}



}
