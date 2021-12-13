package br.com.cotiinformatica.controllers;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.entities.Endereco;
import br.com.cotiinformatica.repositories.IClienteRepository;
import br.com.cotiinformatica.repositories.IEnderecoRepository;
import br.com.cotiinformatica.requests.EnderecoPostRequest;
import br.com.cotiinformatica.responses.EnderecoResponse;

@Controller
@Transactional
public class EnderecoController {

	private static final String ENDPOINT = "/api/v1/enderecos";

	@Autowired
	private IEnderecoRepository enderecoRepository;
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@RequestMapping(path = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody //retorna conteudo em JSON
	public ResponseEntity<EnderecoResponse> post(@RequestBody EnderecoPostRequest request) {
		
		try {
		
			EnderecoResponse response = new EnderecoResponse();
			
			//buscar o cliente informado na base de dados
			Optional<Cliente> cliente = clienteRepository.findById(request.getIdCliente());
			
			//verificar se o cliente não está cadastrado na base de dados
			if(cliente.isEmpty()) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("O Cliente informado não foi encontrado, tente novamente.");				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}						
			
			//capturando os dados do endereço
			Endereco endereco = new Endereco();
			
			endereco.setLogradouro(request.getLogradouro());
			endereco.setBairro(request.getBairro());
			endereco.setCidade(request.getCidade());
			endereco.setComplemento(request.getComplemento());
			endereco.setEstado(request.getEstado());
			endereco.setCep(request.getCep());
			endereco.setCliente(cliente.get());
			
			//cadastrar o endereço com sucesso
			enderecoRepository.save(endereco);
			
			response.setStatusCode(201);
			response.setMensagem("Endereço cadastrado com sucesso.");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);			
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
}





