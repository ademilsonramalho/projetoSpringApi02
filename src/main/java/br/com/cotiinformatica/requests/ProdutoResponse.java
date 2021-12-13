package br.com.cotiinformatica.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
	public class ProdutoResponse {

		private Integer idProduto;
		private String nome;
		private Double preco;
		private Integer quantidade;
		private String foto;
		private String descricao;

}
