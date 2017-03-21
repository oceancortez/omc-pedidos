/**
 * 
 */
package omc.pedidos.ws.rest.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import omc.pedidos.dao.ClienteDAO;
import omc.pedidos.dao.ClienteDAO;
import omc.pedidos.entity.ClienteEntity;

/**
 * @author ocean
 *
 */
@Path("cliente")
@Stateless
public class BucarClienteRestful {
	
	private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class);
		
	@EJB
	private ClienteDAO clienteDAO;
	
	private EntityManager entityManager;
	
	
	public BucarClienteRestful(){
		clienteDAO = new ClienteDAO(entityManager);
	}
	
	
	@GET
	@Path("/buscarTodos")
	public Response obterDadosColaborador(){		
		String responseService="";
		try {
			LOGGER.info("Entou no metodo obterDadosColaborador()");
			
			List<ClienteEntity> clienteEntity = clienteDAO.listarTodosClientes();
			if(CollectionUtils.isEmpty(clienteEntity)){
				return Response.ok().entity(responseService).build();
			}	
			
			responseService = clienteEntity.toString();	
			
			LOGGER.info("Saiu no metodo obterDadosColaborador()[responseService] = ".concat(responseService));
			
		} catch (Exception e) {
			//throw new AtendimentoAPIException("Erro ao executar o metodo obterDadosColaborador",e);
			responseService = e.toString();
			LOGGER.error(e);
		}		
		return Response.ok().entity(responseService).build();

	}

}
