package br.com.cotiinformatica.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.cryptography.MD5Cryptography;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.repositories.IClienteRepository;
import br.com.cotiinformatica.requests.LoginPostRequest;
import br.com.cotiinformatica.responses.LoginResponse;
import br.com.cotiinformatica.security.TokenSecurity;


@Controller
@Transactional
public class LoginController {

	private static final String ENDPOINT = "/api/v1/login";

	@Autowired
	private IClienteRepository clienteRepository;

	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<LoginResponse> post(@RequestBody LoginPostRequest request) {

		LoginResponse response = new LoginResponse();

		try {

			// buscando o cliente atraves do email e da senha
			Cliente cliente = clienteRepository.findByEmailSenha(request.getEmail(),
					MD5Cryptography.encrypt(request.getSenha()));

			// verificar se o cliente foi encontrado
			if (cliente != null) {

				response.setStatusCode(200);
				response.setMensagem("Cliente autenticado com sucesso.");
				response.setAccessToken(TokenSecurity.generateToken(cliente.getEmail()));
				response.setNomeCliente(cliente.getNome());
				response.setEmailCliente(cliente.getEmail());
				
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {

				response.setStatusCode(401);
				response.setMensagem("Acesso negado, cliente inválido.");

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
}





