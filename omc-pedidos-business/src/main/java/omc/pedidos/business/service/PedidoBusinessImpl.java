/**
 * 
 */
package omc.pedidos.business.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import omc.pedidos.business.type.PedidosType;
import omc.pedidos.dao.ClienteDAO;
import omc.pedidos.dao.ClienteDAOImpl;
import omc.pedidos.dao.PedidoDAO;
import omc.pedidos.dao.ProdutoDAO;
import omc.pedidos.entity.ClienteEntity;
import omc.pedidos.entity.PedidoEntity;
import omc.pedidos.entity.ProdutoEntity;
import omc.pedidos.util.JPAUtil;
import omc.pedidos.utils.ParseUtils;

/**
 * @author ocean
 *
 */
@Stateless
public class PedidoBusinessImpl implements PedidoBusiness{
	
	private static final Logger LOGGER = Logger.getLogger(ClienteDAOImpl.class);
	
	private PedidoDAO pedidoDAO;
	
	private ClienteDAO clienteDAO;
	
	private ProdutoDAO produtoDAO;
	
	private EntityManager entityManager;
	
	
	public PedidoBusinessImpl() {
		//TODO usar injeção de dependencia
		entityManager = JPAUtil.getEntityManager();
		pedidoDAO = new PedidoDAO(entityManager);
		clienteDAO = new ClienteDAOImpl();
		produtoDAO = new ProdutoDAO(entityManager);
	}

	@Override
	public List<PedidoEntity> buscarTodosPedidos() {
		
		return pedidoDAO.findAll();
	}

	@Override
	public PedidoEntity salvarPedido(PedidoEntity pedidoEntity) {

		ClienteEntity clienteEntity = new ClienteEntity();		
		clienteEntity.setNome("Testadando meu");
		clienteEntity.setDataCadastro(new Date());
		clienteEntity = clienteDAO.salvar(clienteEntity);
		
		ProdutoEntity produtoEntity = new ProdutoEntity();
		produtoEntity.setNome("novo produto");
		produtoEntity.setValor(new Double("200"));
		produtoEntity.setDataCadastro(new Date());
		produtoEntity = produtoDAO.salvarProduto(produtoEntity);	
		
		
		pedidoEntity.setCodigo(11l);
		pedidoEntity.setClienteEntity(clienteEntity);
		pedidoEntity.setProdutoEntity(produtoEntity);
		//pedidoEntity = pedidoDAO.salvarPedido(pedidoEntity);
		
		
		return pedidoEntity;
	}

	@Override
	public PedidoEntity buscarPedido(Long codigoPedido) {
		// TODO Auto-generated method stub
		return pedidoDAO.getById(codigoPedido);
	}
	
	
	@Override
	public String preparaPedido(String pedido){
		String retornoFinal = "";

		LOGGER.info("Entou no metodo preparaPedido com [pedido = ".concat(pedido).concat("]"));
		
		if(StringUtils.isNotEmpty(pedido)){
			if(pedido.contains("<?xml")){
				LOGGER.info("Pedido do tipo XML");
				
				PedidosType pedidosType = (PedidosType) ParseUtils.parseXmlToObject(pedido, new PedidosType());
				if(pedidosType != null){
					
					String retorno = isArquivoXmlValido(pedidosType);
					if(StringUtils.isNotEmpty(retorno)){
						return  retorno;
					}
					
					
					PedidoEntity pedidoEntity = new PedidoEntity();
					ProdutoEntity produtoEntity = new ProdutoEntity();
					
					for (int i = 0; i < pedidosType.getPedidoTypes().size(); i++) {
						pedidoEntity.setClienteEntity(new ClienteEntity(pedidosType.getPedidoTypes().get(i).getCodigoCliente()));
						
						List<ProdutoEntity> findAll = produtoDAO.findAll();
						if(org.springframework.util.CollectionUtils.isEmpty(findAll)){
							return "Produto não cadastrado [".concat(pedidosType.getPedidoTypes().get(i).getNomeProduto());
						}
						
						for (ProdutoEntity produto : findAll) {
							if(produto.getNome().equalsIgnoreCase(pedidosType.getPedidoTypes().get(i).getNomeProduto())){
								produtoEntity.setCodigo(produto.getCodigo());
//								produtoEntity.setDataCadastro(produto.getDataCadastro());
//								produtoEntity.setDataUltimaAlteracao(new Date());
//								produtoEntity.setNome(produto.getNome());
//								produtoEntity.setValor(pro);
								
							}
						}
						
						pedidoEntity.setProdutoEntity(produtoEntity);						
						
						pedidoEntity.setCodigo(pedidosType.getPedidoTypes().get(i).getNumeroControle());
						
						if(pedidosType.getPedidoTypes().get(i).getDataCadastro() == null){
							pedidoEntity.setDataCadastro(new Date());
						}else{
							pedidoEntity.setDataCadastro(pedidosType.getPedidoTypes().get(i).getDataCadastro());
						}
						
						boolean isPedidoSalvo = pedidoDAO.salvarPedido(pedidoEntity);
						if(isPedidoSalvo){
							retornoFinal = "Pedido salvo com sucesso!";
						}else{
							retornoFinal = "Erro ao salvar o pedido de numero = ".concat(pedidoEntity.getCodigo().toString());
						}
												
					}
					
					
				}
				
			}else{
					LOGGER.info("Pedido do tipo JSON");
				
				}
			}
				
				
		return retornoFinal;
	}

	private String isArquivoXmlValido(PedidosType pedidosType) {
		String retorno = "";
		if(pedidosType.getPedidoTypes().size() > 9){
			return "Tamanho de arquivo inválido";
		}
		
		for (int i = 0; i < pedidosType.getPedidoTypes().size(); i++) {
			
			if(pedidosType.getPedidoTypes().get(i).getNumeroControle() == null){
				return "NumeroControle não pode ser nulo";
			}
			
			if(pedidosType.getPedidoTypes().get(i).getValor() == null){
				return "Campo Valor não pode ser nulo";
			}
			
			if(StringUtils.isEmpty(pedidosType.getPedidoTypes().get(i).getNomeProduto())){
				return "Campo Nome do Produto não pode ser nulo";
			}
			
			if(pedidosType.getPedidoTypes().get(i).getCodigoCliente() == null){
				return "Campo Codigo do Cliente não pode ser nulo";
			}
			
			if(pedidosType.getPedidoTypes().get(i).getQuantidade() == null){
				pedidosType.getPedidoTypes().get(i).setQuantidade(1l);
			}						
		}
		
		return retorno;
	}
	
	private boolean isPedidoValido(String pedido){
		
		LOGGER.info("Entou no metodo isPedidoValido com [pedido = ".concat(pedido).concat("]"));
		
		if(StringUtils.isNotEmpty(pedido)){
			if(pedido.contains("<?xml")){
				LOGGER.info("Pedido do tipo XML");
				
				PedidosType pedidosType = (PedidosType) ParseUtils.parseXmlToObject(pedido, PedidosType.class);
				if(pedidosType != null){
					
				}
				
			}else{
				LOGGER.info("Pedido do tipo JSON");
				
			}
		}
		
		LOGGER.info("Saiu no metodo isPedidoValido com [pedido = ".concat(pedido).concat("]"));
		
		return false;
	}
	
	

}
