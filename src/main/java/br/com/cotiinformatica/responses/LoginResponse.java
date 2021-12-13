package br.com.cotiinformatica.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
	private Integer statusCode;
		private String mensagem;
		private String accessToken;
		private String nomeCliente;
		private String emailCliente;
}
