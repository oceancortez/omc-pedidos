package omc.pedidos.business;

import java.util.List;

import javax.ejb.Local;

import omc.pedidos.entity.PedidoEntity;

/**
 * @author ocean
 *
 */
@Local
public interface PedidoBusiness {
	
	
	List<PedidoEntity> buscarTodosPedidos();
	
	PedidoEntity salvarPedido(PedidoEntity pedidoEntity);
	
	PedidoEntity buscarPedido(Long codigoPedido);
	

}
