/**
 * 
 */
package omc.pedidos.ws.rest.facade;

import java.util.List;

import javax.ejb.EJB;

import omc.pedidos.business.service.PedidoBusiness;
import omc.pedidos.business.service.PedidoBusinessImpl;
import omc.pedidos.entity.PedidoEntity;

/**
 * @author ocean
 *
 */
public class PedidoFacade {
	
	@EJB
	private PedidoBusiness pedidoBusiness;
	
	
	public PedidoFacade() {
		pedidoBusiness = new PedidoBusinessImpl();
	}

	public List<PedidoEntity> buscarTodosPedidos() {
		// TODO Auto-generated method stub
		return pedidoBusiness.buscarTodosPedidos();
	}

	public PedidoEntity buscarPedido(Long codigoPedido) {
		// TODO Auto-generated method stub
		return pedidoBusiness.buscarPedido(codigoPedido);
	}

	public String cadastrarPedido(String pedido) {
		// TODO Auto-generated method stub
		return pedidoBusiness.cadastrarPedido(pedido);
	}

}
