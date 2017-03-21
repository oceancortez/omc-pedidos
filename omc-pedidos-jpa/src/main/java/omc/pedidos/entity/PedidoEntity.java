/**
 * 
 */
package omc.pedidos.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author ocean
 *
 */

@Entity
@Table(name = "pedido", schema = "omc")
public class PedidoEntity {
	
	@Id
    @Column(name = "CODPED")
	private Long codigo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "CODCLI")
	private ClienteEntity clienteEntity;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "CODPRD")
	private ProdutoEntity produtoEntity;
	
	@Column(name = "DATCADPED")
	private Date dataCadastro;
	
	@Column(name = "DATULTALTPED")
	private Date dataUltimaAlteracao;

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the clienteEntity
	 */
	public ClienteEntity getClienteEntity() {
		return clienteEntity;
	}

	/**
	 * @param clienteEntity the clienteEntity to set
	 */
	public void setClienteEntity(ClienteEntity clienteEntity) {
		this.clienteEntity = clienteEntity;
	}

	/**
	 * @return the produtoEntity
	 */
	public ProdutoEntity getProdutoEntity() {
		return produtoEntity;
	}

	/**
	 * @param produtoEntity the produtoEntity to set
	 */
	public void setProdutoEntity(ProdutoEntity produtoEntity) {
		this.produtoEntity = produtoEntity;
	}

	/**
	 * @return the dataCadastro
	 */
	public Date getDataCadastro() {
		return dataCadastro;
	}

	/**
	 * @param dataCadastro the dataCadastro to set
	 */
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * @return the dataUltimaAlteracao
	 */
	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	/**
	 * @param dataUltimaAlteracao the dataUltimaAlteracao to set
	 */
	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}
	
	

}
