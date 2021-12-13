package br.com.cotiinformatica.responses;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetalhamentoVendasResponse {

	private Integer idVenda;
	private Date dataHora;
	private Double valor;
	
	private List<ItemVendaResponse> itensVenda;
	
}
