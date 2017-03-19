/**
 * 
 */
package omc.pedidos.ws.rest.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import omc.pedidos.business.service.PedidoBusiness;
import omc.pedidos.business.service.PedidoBusinessImpl;
import omc.pedidos.entity.PedidoEntity;

/**
 * @author ocean
 *
 */
@Path("pedido")
@Stateless
public class PedidoRestful {
	
	private static final Logger LOGGER = Logger.getLogger(PedidoRestful.class);
		
	
	@EJB
	private PedidoBusiness pedidoBusiness;

	
	public  PedidoRestful() {
		// TODO Usar injeção de dependencia
		pedidoBusiness = new PedidoBusinessImpl();
	}
	
	
	@GET
	@Path("/buscarTodos")
	public Response obterDadosPedidos(){		
		String responseService="";
		try {
			LOGGER.info("Entou no metodo obterDadosPedidos()");			
			
			List<PedidoEntity> pedidoEntities = pedidoBusiness.buscarTodosPedidos();
			
			if(CollectionUtils.isEmpty(pedidoEntities)){
				return Response.ok().entity(responseService).build();
			}	
			
			responseService = pedidoEntities.toString();	
			
			LOGGER.info("Saiu no metodo obterDadosPedidos()[responseService] = ".concat(responseService));
			
		} catch (Exception e) {
			responseService = e.toString();
			LOGGER.error(e);
		}		
		return Response.ok().entity(responseService).build();

	}
	
	@GET
	@Path("/buscarpedido")
	public Response obterDadosPedidos(@QueryParam("codigoPedido") Long codigoPedido){		
		String responseService="";
		try {
			LOGGER.info("Entou no metodo obterDadosPedidos()");			
			
			final PedidoEntity pedido = pedidoBusiness.buscarPedido(codigoPedido);
			
			if(pedido == null){
				return Response.ok().entity(responseService).build();
			}				
			responseService = pedido.getProdutoEntity().getNome().toString();	
			
			LOGGER.info("Saiu no metodo obterDadosPedidos()[responseService] = ".concat(responseService));
			
		} catch (Exception e) {
			responseService = e.toString();
			LOGGER.error(e);
		}		
		return Response.ok().entity(responseService).build();

	}
	
	
	@POST
	@Path("/cadastrar")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response cadastrarPedido(final String pedido){		
		String responseService="";
		try {
			LOGGER.info("Entou no metodo cadastrarPedido()");			
					
			responseService = pedidoBusiness.preparaPedido(pedido);
			
			LOGGER.info("Saiu no metodo cadastrarPedido()[responseService] = ".concat(responseService));
			
		} catch (Exception e) {
			responseService = e.toString();
			LOGGER.error(e);
		}		
		return Response.ok().entity(responseService).build();

	}

}