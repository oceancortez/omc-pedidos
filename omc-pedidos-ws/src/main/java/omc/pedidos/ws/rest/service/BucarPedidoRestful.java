/**
 * 
 */
package omc.pedidos.ws.rest.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import omc.pedidos.dao.PedidoDAO;
import omc.pedidos.dao.PedidoDAOImpl;
import omc.pedidos.entity.PedidoEntity;

/**
 * @author ocean
 *
 */
@Path("pedido")
@Stateless
public class BucarPedidoRestful {
	
	private static final Logger LOGGER = Logger.getLogger(BucarPedidoRestful.class);
		
	@EJB
	private PedidoDAO pedidoDAO;
	
	
	public BucarPedidoRestful(){
		pedidoDAO = new PedidoDAOImpl();
	}
	
	
	@GET
	@Path("/buscarTodos")
	public Response obterDadosPedidos(){		
		String responseService="";
		try {
			LOGGER.info("Entou no metodo obterDadosPedidos()");
			
			List<PedidoEntity> pedidoEntities = pedidoDAO.listarTodosPedidos();
			if(CollectionUtils.isEmpty(pedidoEntities)){
				return Response.ok().entity(responseService).build();
			}	
			
			responseService = pedidoEntities.toString();	
			
			LOGGER.info("Saiu no metodo obterDadosPedidos()[responseService] = ".concat(responseService));
			
		} catch (Exception e) {
			//throw new AtendimentoAPIException("Erro ao executar o metodo obterDadosColaborador",e);
			responseService = e.toString();
			LOGGER.error(e);
		}		
		return Response.ok().entity(responseService).build();

	}

}
