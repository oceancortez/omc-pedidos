/**
 * 
 */
package omc.pedidos.business.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import omc.pedidos.business.type.PedidoType;
import omc.pedidos.business.type.PedidosType;
import omc.pedidos.dao.ClienteDAO;
import omc.pedidos.dao.ClienteDAO;
import omc.pedidos.dao.PedidoDAO;
import omc.pedidos.dao.ProdutoDAO;
import omc.pedidos.entity.ClienteEntity;
import omc.pedidos.entity.PedidoEntity;
import omc.pedidos.entity.ProdutoEntity;
import omc.pedidos.util.JPAUtil;
import omc.pedidos.utils.ConvertUtils;

/**
 * @author ocean
 *
 */
@Stateless
public class PedidoBusinessImpl implements PedidoBusiness {

	private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class);

	private PedidoDAO pedidoDAO;

	private ClienteDAO clienteDAO;
	
	private ProdutoDAO produtoDAO;

	private EntityManager entityManager;

	/**
	 * Instantiates a new pedido business impl.
	 */
	public PedidoBusinessImpl() {
		// TODO usar injeção de dependencia
		entityManager = JPAUtil.getEntityManager();
		pedidoDAO = new PedidoDAO(entityManager);
		clienteDAO = new ClienteDAO(entityManager);
		produtoDAO = new ProdutoDAO(entityManager);
	}

	@Override
	public List<PedidoEntity> buscarTodosPedidos() {
		return pedidoDAO.findAll();
	}

	
	@Override
	public PedidoEntity buscarPedido(Long codigoPedido) {
		// TODO Auto-generated method stub
		return pedidoDAO.getById(codigoPedido);
	}


	@Override
	public String cadastrarPedido(String pedido) {
		LOGGER.info("Entou no metodo preparaPedido com [pedido = ".concat(pedido).concat("]"));
		String retornoFinal = StringUtils.EMPTY;
		
		if (StringUtils.isNotEmpty(pedido)) {
			if (pedido.contains("<?xml")) {
				retornoFinal = cadastrarPedidoXml(pedido);
			} else {							
				retornoFinal = cadastrarPedidoJson(pedido);
			}
		}

		return retornoFinal;
	}

	/**
	 * Cadastrar pedido json.
	 *
	 * @param pedido the pedido
	 * @param retornoFinal the retorno final
	 * @return the string
	 */
	private String cadastrarPedidoJson(String pedido) {
		LOGGER.info("Pedido do tipo JSON");
		String retornoFinal = StringUtils.EMPTY;
		
		PedidosType pedidosType = (PedidosType) ConvertUtils.convertJsonToObject(pedido, new PedidosType());
		
		 retornoFinal = isArquivoValido(pedidosType);
		 if(StringUtils.isNotBlank(retornoFinal)){
			 return retornoFinal;
		 }
		 
		 retornoFinal = salvarPedido(retornoFinal, pedidosType);
		 
		return retornoFinal;
	}

	/**
	 * Cadastrar pedido xml.
	 *
	 * @param pedido the pedido
	 * @param retornoFinal the retorno final
	 * @return the string
	 */
	private String cadastrarPedidoXml(String pedido) {
		LOGGER.info("Pedido do tipo XML");
		String retornoFinal = StringUtils.EMPTY;

		PedidosType pedidosType = (PedidosType) ConvertUtils.parseXmlToObject(pedido, new PedidosType());
		if (pedidosType != null) {

			retornoFinal = isArquivoValido(pedidosType);
			if (StringUtils.isNotEmpty(retornoFinal)) {
				return retornoFinal;
			}

			retornoFinal = salvarPedido(retornoFinal, pedidosType);

		}
		return retornoFinal;
	}

	/**
	 * Salvar pedido.
	 *
	 * @param retornoFinal the retorno final
	 * @param pedidosType the pedidos type
	 * @return the string
	 */
	private String salvarPedido(String retornoFinal, PedidosType pedidosType) {		
			
		for (int i = 0; i < pedidosType.getPedidoType().size(); i++) {

			PedidoEntity pedidoEntity = new PedidoEntity();
			ProdutoEntity produtoEntity = new ProdutoEntity();
			ClienteEntity clienteEntity = new ClienteEntity();			
			
			clienteEntity = clienteDAO.getById(pedidosType.getPedidoType().get(i).getCodigoCliente());			
			pedidoEntity.setClienteEntity(clienteEntity);

			produtoEntity.setNome(pedidosType.getPedidoType().get(i).getNomeProduto());
			produtoEntity.setQuantidade(pedidosType.getPedidoType().get(i).getQuantidade().intValue());
			produtoEntity.setValor(pedidosType.getPedidoType().get(i).getValor());			
			aplicarRegraDescontoValor(produtoEntity);
			
			produtoEntity = produtoDAO.salvarProduto(produtoEntity);
			pedidoEntity.setProdutoEntity(produtoEntity);

			pedidoEntity.setCodigo(pedidosType.getPedidoType().get(i).getNumeroControle());
			if (pedidosType.getPedidoType().get(i).getDataCadastro() == null) {
				pedidoEntity.setDataCadastro(new Date());
			} else {
				pedidoEntity.setDataCadastro(pedidosType.getPedidoType().get(i).getDataCadastro());
			}
			boolean isPedidoSalvo = pedidoDAO.salvarPedido(pedidoEntity);
			if (isPedidoSalvo) {				
				retornoFinal = "Pedido salvo com sucesso!";
			} else {
				retornoFinal = "Erro ao salvar o pedido de numero = ".concat(pedidoEntity.getCodigo().toString());
			}
		}		
		pedidoDAO.getEntityManager().close();
		return retornoFinal;
	}

	/**
	 * Aplicar regra desconto valor.
	 *
	 * @param produtoEntity the produto entity
	 */
	private void aplicarRegraDescontoValor(ProdutoEntity produtoEntity) {
		if(produtoEntity.getQuantidade() > 4 && produtoEntity.getQuantidade() < 10){
			Double descontoCinco = produtoEntity.getValor() * 0.95;
			produtoEntity.setValor(descontoCinco);
			
		}else if(produtoEntity.getQuantidade() >= 10){
			
			Double descontoCinco = produtoEntity.getValor() * 0.90;
			produtoEntity.setValor(descontoCinco);
			
		}
	}

	/**
	 * Checks if is arquivo xml valido.
	 *
	 * @param pedidosType the pedidos type
	 * @return the string
	 */
	private String isArquivoValido(PedidosType pedidosType) {
		String retorno = "";
		if (pedidosType.getPedidoType().size() > 9) {
			return "Tamanho de arquivo inválido";
		}
		List<PedidoType> expurgoPedidos = new ArrayList<>();

		for (int i = 0; i < pedidosType.getPedidoType().size(); i++) {
			
			PedidoEntity pedidoEntityBase = pedidoDAO.getById(pedidosType.getPedidoType().get(i).getNumeroControle());
			if(pedidoEntityBase != null){
				expurgoPedidos.add(pedidosType.getPedidoType().get(i));
			}
			
			if (pedidosType.getPedidoType().get(i).getNumeroControle() == null) {
				return "NumeroControle não pode ser nulo";
			}

			if (pedidosType.getPedidoType().get(i).getValor() == null) {
				return "Campo Valor não pode ser nulo";
			}

			if (StringUtils.isEmpty(pedidosType.getPedidoType().get(i).getNomeProduto())) {
				return "Campo Nome do Produto não pode ser nulo";
			}

			if (pedidosType.getPedidoType().get(i).getCodigoCliente() == null) {
				return "Campo Codigo do Cliente não pode ser nulo";
			}

			if (pedidosType.getPedidoType().get(i).getQuantidade() == null) {
				pedidosType.getPedidoType().get(i).setQuantidade(1l);
			}
		}
		//Remove da lista pedidosType.getPedidoType() os pedidos da lista expurgoPedidos 
		@SuppressWarnings("unchecked")
		Collection<PedidoType> subtract = CollectionUtils.subtract(pedidosType.getPedidoType(), expurgoPedidos);		
		pedidosType.setPedidoType(new ArrayList<>(subtract));
		
			if(pedidosType.getPedidoType().isEmpty()){
				return "Não houveram pedidos válidos para serem cadastrados";
			}
			
		return retorno;
	}
}
