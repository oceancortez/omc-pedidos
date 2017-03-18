/**
 * 
 */
package omc.pedidos.dao;

import java.util.List;

import javax.ejb.Local;

import omc.pedidos.entity.ClienteEntity;

/**
 * @author ocean
 *
 */
@Local
public interface ClienteDAO {
	
	
	List<ClienteEntity> listarTodosClientes();
}
