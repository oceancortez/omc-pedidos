/**
 * 
 */
package omc.pedidos.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author ocean
 *
 */

@Entity(name = "pedido")
public class PedidoEntity {
	
	@Id
    @Column(name = "CODPED")
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "CODCLI")
	private PedidoEntity pedidoEntity;
	
	@ManyToOne
	@JoinColumn(name = "CODPRD")
	private ProdutoEntity produtoEntity;
	
	@Column(name = "DATCADCLI")
	private Date dataCadastro;
	
	@Column(name = "DATULTALTCLI")
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
	 * @return the pedidoEntity
	 */
	public PedidoEntity getPedidoEntity() {
		return pedidoEntity;
	}

	/**
	 * @param pedidoEntity the pedidoEntity to set
	 */
	public void setPedidoEntity(PedidoEntity pedidoEntity) {
		this.pedidoEntity = pedidoEntity;
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
