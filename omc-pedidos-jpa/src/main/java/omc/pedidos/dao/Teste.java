package omc.pedidos.dao;

import java.util.List;

import javax.inject.Inject;

import omc.pedidos.entity.ClienteEntity;

public class Teste {
	@Inject
	static	ClienteDAO dao;
	
	public static void main(String[] args) {
		
		
		testCliente();
		

	}
	
	
	public static void testCliente(){
		List<ClienteEntity> list = dao.listarTodosClientes();
		
		for (ClienteEntity clienteEntity : list) {
			System.out.println(clienteEntity.getNome());
		}
		
	}

}
