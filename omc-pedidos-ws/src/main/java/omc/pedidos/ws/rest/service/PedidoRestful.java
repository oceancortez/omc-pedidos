/**
 * 
 */
package omc.pedidos.ws.rest.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import omc.pedidos.entity.PedidoEntity;
import omc.pedidos.ws.rest.facade.PedidoFacade;

/**
 * @author ocean
 *
 */
@Path("pedido")
public class PedidoRestful {
	
	private static final Logger LOGGER = Logger.getLogger(PedidoRestful.class);
		
	
	private PedidoFacade pedidoFacade;

	
	public  PedidoRestful() {
		pedidoFacade = new PedidoFacade();
	}
	
	
	/**
	 * Buscar todos.
	 *
	 * @return the response
	 */
	@GET
	@Path("/buscarTodos")
	public Response buscarTodos(){		
		String responseService="";
		try {
			LOGGER.info("Entou no metodo obterDadosPedidos()");			
			
			List<PedidoEntity> pedidoEntities = pedidoFacade.buscarTodosPedidos();
			
			if(CollectionUtils.isEmpty(pedidoEntities)){
				responseService = "Não existem pedidos cadastrados!";
				LOGGER.info("Saiu no metodo obterDadosPedidos()");	
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
	
	/**
	 * Buscar pedido.
	 *
	 * @param codigoPedido the codigo pedido
	 * @return the response
	 */
	@GET
	@Path("/buscarPedido")
	public Response buscarPedido(@QueryParam("codigoPedido") Long codigoPedido){		
		String responseService="";
		try {
			LOGGER.info("Entou no metodo obterDadosPedidos()");			
			
			final PedidoEntity pedido = pedidoFacade.buscarPedido(codigoPedido);
			
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
	
	
	/**
	 * Cadastrar pedido.
	 *
	 * @param pedido the pedido
	 * @return the response
	 */
	@POST
	@Path("/cadastrar")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response cadastrarPedido(final String pedido){		
		String retorno="";
		try {
			LOGGER.info("Entou no metodo cadastrarPedido()");			
					
			retorno = pedidoFacade.cadastrarPedido(pedido);
			
			LOGGER.info("Saiu no metodo cadastrarPedido()[responseService] = ".concat(retorno));
			
		} catch (Exception e) {
			retorno = e.toString();
			LOGGER.error(e);
		}		
		return Response.ok().entity(retorno).build();

	}

}
