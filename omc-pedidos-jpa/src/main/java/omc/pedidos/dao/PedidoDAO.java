/**
 * 
 */
package omc.pedidos.dao;

import java.util.List;

import javax.ejb.Local;

import omc.pedidos.entity.PedidoEntity;

/**
 * @author ocean
 *
 */
@Local
public interface PedidoDAO {
	
	
	List<PedidoEntity> listarTodosPedidos();
}
