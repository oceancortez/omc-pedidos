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
public class PedidoBusinessImpl implements PedidoBusiness {

	private static final Logger LOGGER = Logger.getLogger(ClienteDAOImpl.class);

	private PedidoDAO pedidoDAO;

	private ClienteDAO clienteDAO;

	private ProdutoDAO produtoDAO;

	private EntityManager entityManager;

	public PedidoBusinessImpl() {
		// TODO usar injeção de dependencia
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
		// pedidoEntity = pedidoDAO.salvarPedido(pedidoEntity);

		return pedidoEntity;
	}

	@Override
	public PedidoEntity buscarPedido(Long codigoPedido) {
		// TODO Auto-generated method stub
		return pedidoDAO.getById(codigoPedido);
	}

	@Override
	public String preparaPedido(String pedido) {
		String retornoFinal = "";

		LOGGER.info("Entou no metodo preparaPedido com [pedido = ".concat(pedido).concat("]"));

		if (StringUtils.isNotEmpty(pedido)) {
			if (pedido.contains("<?xml")) {
				LOGGER.info("Pedido do tipo XML");

				PedidosType pedidosType = (PedidosType) ParseUtils.parseXmlToObject(pedido, new PedidosType());
				if (pedidosType != null) {

					String retorno = isArquivoXmlValido(pedidosType);
					if (StringUtils.isNotEmpty(retorno)) {
						return retorno;
					}

					for (int i = 0; i < pedidosType.getPedidoType().size(); i++) {

						PedidoEntity pedidoEntity = new PedidoEntity();
						ProdutoEntity produtoEntity = new ProdutoEntity();

						pedidoEntity.setClienteEntity(new ClienteEntity(pedidosType.getPedidoType().get(i).getCodigoCliente()));

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
							retornoFinal = "Erro ao salvar o pedido de numero = "
									.concat(pedidoEntity.getCodigo().toString());
						}

					}

				}

			} else {
				LOGGER.info("Pedido do tipo JSON");

			}
		}

		return retornoFinal;
	}

	private void aplicarRegraDescontoValor(ProdutoEntity produtoEntity) {
		if(produtoEntity.getQuantidade() > 4 && produtoEntity.getQuantidade() < 10){
			Double descontoCinco = produtoEntity.getValor() * 0.95;
			produtoEntity.setValor(descontoCinco);
			
		}else if(produtoEntity.getQuantidade() >= 10){
			
			Double descontoCinco = produtoEntity.getValor() * 0.90;
			produtoEntity.setValor(descontoCinco);
			
		}
	}

	private String isArquivoXmlValido(PedidosType pedidosType) {
		String retorno = "";
		if (pedidosType.getPedidoType().size() > 9) {
			return "Tamanho de arquivo inválido";
		}

		for (int i = 0; i < pedidosType.getPedidoType().size(); i++) {

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

		return retorno;
	}

	private boolean isPedidoValido(String pedido) {

		LOGGER.info("Entou no metodo isPedidoValido com [pedido = ".concat(pedido).concat("]"));

		if (StringUtils.isNotEmpty(pedido)) {
			if (pedido.contains("<?xml")) {
				LOGGER.info("Pedido do tipo XML");

				PedidosType pedidosType = (PedidosType) ParseUtils.parseXmlToObject(pedido, PedidosType.class);
				if (pedidosType != null) {

				}

			} else {
				LOGGER.info("Pedido do tipo JSON");

			}
		}

		LOGGER.info("Saiu no metodo isPedidoValido com [pedido = ".concat(pedido).concat("]"));

		return false;
	}

}
