package omc.pedidos.business.type;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pedidos")
@XmlAccessorType(XmlAccessType.FIELD)
public class PedidosType {

	
	@XmlElement(name = "pedido")
	private List<PedidoType> pedidoTypes;

	/**
	 * @return the pedidoTypes
	 */
	public List<PedidoType> getPedidoTypes() {
		return pedidoTypes;
	}

	/**
	 * @param pedidoTypes the pedidoTypes to set
	 */	
	

	public void setPedidoTypes(List<PedidoType> pedidoTypes) {
		this.pedidoTypes = pedidoTypes;
	}

}
