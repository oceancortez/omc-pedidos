/**
 * 
 */
package omc.pedidos.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import omc.pedidos.dao.PedidoDAO;
import omc.pedidos.entity.ClienteEntity;
import omc.pedidos.entity.PedidoEntity;
import omc.pedidos.entity.ProdutoEntity;
import omc.pedidos.util.JPAUtil;

/**
 * @author ocean
 *
 */
@Stateless
public class PedidoBusinessImpl implements PedidoBusiness{
	
	private PedidoDAO pedidoDAO;
	
	private EntityManager entityManager;
	
	
	public PedidoBusinessImpl() {
		//TODO usar injeção de dependencia
		entityManager = JPAUtil.getEntityManager();
		pedidoDAO = new PedidoDAO(entityManager);
	}

	@Override
	public List<PedidoEntity> buscarTodosPedidos() {
		
		return pedidoDAO.findAll();
	}

	@Override
	public PedidoEntity salvarPedido(PedidoEntity pedidoEntity) {
		// TODO Auto-generated method stub
		
		ClienteEntity clienteEntity = new ClienteEntity();		
		clienteEntity.setNome("Testadando meu");
		
		ProdutoEntity produtoEntity = new ProdutoEntity();
		produtoEntity.setNome("novo produto");
		produtoEntity.setValor(new Double("200"));
		
		pedidoEntity.setCodigo(11l);
		pedidoEntity.setClienteEntity(clienteEntity);
		pedidoEntity.setProdutoEntity(produtoEntity);	
		
		
		return pedidoDAO.salvarPedido(pedidoEntity);
	}

	@Override
	public PedidoEntity buscarPedido(Long codigoPedido) {
		// TODO Auto-generated method stub
		return pedidoDAO.getById(codigoPedido);
	}
	
	

}
