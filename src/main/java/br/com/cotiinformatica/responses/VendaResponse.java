package br.com.cotiinformatica.responses;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendaResponse {

	private String mensagem;
	private Integer statusCode;
	private Double totalVenda;
	private Date dataVenda;
}
