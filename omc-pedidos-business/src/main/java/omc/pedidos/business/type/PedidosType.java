package omc.pedidos.business.type;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "pedidos")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidosType {

	
	@XmlElement(name = "pedido")
	@JsonProperty(value = "pedido")
	private List<PedidoType> pedidoType;

	/**
	 * @return the pedidoTypes
	 */
	
	public List<PedidoType> getPedidoType() {
		return pedidoType;
	}

	/**
	 * @param pedidoTypes the pedidoTypes to set
	 */	
	public void setPedidoType(List<PedidoType> pedidoTypes) {
		this.pedidoType = pedidoTypes;
	}

}
