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
import br.com.cotiinformatica.requests.ClientePostRequest;
import br.com.cotiinformatica.responses.ClienteResponse;


@Controller
@Transactional
public class ClienteController {

	private static final String ENDPOINT = "/api/v1/clientes";

	@Autowired
	private IClienteRepository clienteRepository;

	@RequestMapping(path = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody //retorna conteudo em JSON
	public ResponseEntity<ClienteResponse> post(@RequestBody ClientePostRequest request) {
		
		try {
		
			ClienteResponse response = new ClienteResponse();
			
			//verificar se o cpf informado já está cadastrado no sistema
			if(clienteRepository.findByCpf(request.getCpf()) != null) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("O cpf informado '"+ request.getCpf() +"' já está cadastrado, tente outro.");				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			//verificar se o telefone informado já está cadastrado no sistema
			if(clienteRepository.findByTelefone(request.getTelefone()) != null) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("O telefone informado '"+ request.getTelefone() +"' já está cadastrado, tente outro.");				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			//verificar se o email informado já está cadastrado no sistema
			if(clienteRepository.findByEmail(request.getEmail()) != null) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("O email informado '"+ request.getEmail() +"' já está cadastrado, tente outro.");				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			//verificar se as senhas não são iguais
			if(!request.getSenha().equals(request.getSenhaConfirmacao())) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("Senhas não conferem, tente novamente.");				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			//capturando os dados do cliente
			Cliente cliente = new Cliente();
			
			cliente.setNome(request.getNome());
			cliente.setEmail(request.getEmail());
			cliente.setTelefone(request.getTelefone());
			cliente.setCpf(request.getCpf());
			cliente.setSenha(MD5Cryptography.encrypt(request.getSenha()));
			
			//cadastrar o cliente com sucesso
			clienteRepository.save(cliente);
			
			response.setStatusCode(201);
			response.setMensagem("Cliente '"+ cliente.getNome() +"', cadastrado com sucesso.");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);			
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
}

















