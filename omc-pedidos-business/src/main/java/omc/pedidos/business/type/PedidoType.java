/**
 * 
 */
package omc.pedidos.business.type;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author ocean
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoType {	
	

	@XmlElement
	private Long numeroControle;
	
	@XmlElement
	private Date dataCadastro;

	@XmlElement
	private String nomeProduto;

	@XmlElement
	private Double valor;

	@XmlElement
	private Long quantidade;
	
	@XmlElement
	private Long codigoCliente;

	/**
	 * @return the numeroControle
	 */
	@JsonProperty
	public Long getNumeroControle() {
		return numeroControle;
	}

	/**
	 * @param numeroControle the numeroControle to set
	 */
	

	public void setNumeroControle(Long numeroControle) {
		this.numeroControle = numeroControle;
	}

	/**
	 * @return the dataCadastro
	 */
	@JsonProperty
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
	 * @return the nomeProduto
	 */
	@JsonProperty
	public String getNomeProduto() {
		return nomeProduto;
	}

	/**
	 * @param nomeProduto the nomeProduto to set
	 */	
	
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	/**
	 * @return the valor
	 */
	@JsonProperty
	public Double getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}

	/**
	 * @return the quantidade
	 */
	@JsonProperty
	public Long getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the codigoCliente
	 */
	@JsonProperty
	public Long getCodigoCliente() {
		return codigoCliente;
	}

	/**
	 * @param codigoCliente the codigoCliente to set
	 */
	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	
	
	

}
