package br.com.cotiinformatica.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemVendaResponse {

	private Integer idItemVenda;
	private Double precoUnitario;
	private Integer quantidade;
	private Double total;
	private String nomeProduto;
	private String descricaoProduto;
	private String fotoProduto;

}
