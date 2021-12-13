package br.com.cotiinformatica.requests;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendaPostRequest {

	private String emailCliente;
	private List<ItemVendaPostRequest> itensVenda;
}





