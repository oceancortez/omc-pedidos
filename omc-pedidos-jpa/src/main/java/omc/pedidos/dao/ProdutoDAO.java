/**
 * 
 */
package omc.pedidos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import omc.pedidos.entity.ProdutoEntity;

/**
 * @author ocean
 *
 */
public class ProdutoDAO extends GenericDAO<Long, ProdutoEntity>{
	
	private static final Logger LOGGER = Logger.getLogger(ProdutoDAO.class);
	
	public ProdutoDAO(EntityManager entityManager) {
		super(entityManager);
	}

	

	public List<ProdutoEntity> listarTodosProdutos() {
		List<ProdutoEntity> produtos = null;
		try {
			LOGGER.info("Entrou no método listarTodosProdutos");
			//Para forçar o JPA ir buscar os dados no banco 
			//manager = JPAUtil.getEntityManager();
			
			final Query query = this.getEntityManager().createQuery("SELECT p FROM PedidoEntity p", ProdutoEntity.class);

			produtos = query.getResultList();

			LOGGER.info("Saiu do método listarTodosProdutos com o total de registros = " + produtos.size());
		} catch (Exception e) {
			LOGGER.error("Erro no método listarTodosProdutos() >>"  + e.getMessage() );
		}
		return produtos;
	} 
	
	
	public ProdutoEntity salvarProduto(ProdutoEntity produtoEntity){
		
		try {
			LOGGER.info("Erro no método salvarProduto() >>");
			this.getEntityManager().getTransaction().begin();
			produtoEntity = this.getEntityManager().merge(produtoEntity);
			this.getEntityManager().getTransaction().commit();
			LOGGER.info("Erro no método salvarProduto  produtoEntity.getCodigo() >>".concat(produtoEntity.getCodigo().toString()));
		} catch (Exception e) {
			LOGGER.error("Erro no método salvarProduto() >>"  + e.getMessage());
		}
		
		return produtoEntity;
	}



}
