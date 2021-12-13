package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.entities.ItemVenda;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.entities.Venda;
import br.com.cotiinformatica.repositories.IClienteRepository;
import br.com.cotiinformatica.repositories.IItemVendaRepository;
import br.com.cotiinformatica.repositories.IProdutoRepository;
import br.com.cotiinformatica.repositories.IVendaRepository;
import br.com.cotiinformatica.requests.ItemVendaPostRequest;
import br.com.cotiinformatica.requests.VendaPostRequest;
import br.com.cotiinformatica.responses.DetalhamentoVendasResponse;
import br.com.cotiinformatica.responses.HistoricoVendasResponse;
import br.com.cotiinformatica.responses.ItemVendaResponse;
import br.com.cotiinformatica.responses.VendaResponse;

@Controller
@Transactional
public class VendaController {

	private static final String ENDPOINT = "/api/v1/vendas";

	@Autowired
	private IVendaRepository vendaRepository;

	@Autowired
	private IClienteRepository clienteRepository;

	@Autowired
	private IProdutoRepository produtoRepository;

	@Autowired
	private IItemVendaRepository itemVendaRepository;

	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<VendaResponse> post(@RequestBody VendaPostRequest request) {

		VendaResponse response = new VendaResponse();
		
		try {
			
			//verificar se o cliente é válido
			Cliente cliente = clienteRepository.findByEmail(request.getEmailCliente());
			if(cliente == null) {
				response.setStatusCode(400);
				response.setMensagem("Cliente não encontrado.");
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(response);	
			}
			
			Double totalVenda = 0.;
			
			//verificar se cada produto enviado é válido
			List<ItemVenda> itensVenda = new ArrayList<ItemVenda>();			
			for(ItemVendaPostRequest item : request.getItensVenda()) {
				
				Optional<Produto> produto = produtoRepository.findById(item.getIdProduto());
				
				if(produto.isEmpty()) {
					response.setStatusCode(400);
					response.setMensagem("Os produtos enviados são inválidos, por favor verifique.");
					
					return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
							.body(response);	
				}						
						
				ItemVenda itemVenda = new ItemVenda();
				itemVenda.setProduto(produto.get());
				itemVenda.setPrecoUnitario(produto.get().getPreco());
				itemVenda.setQuantidade(item.getQuantidade());
				
				itensVenda.add(itemVenda);
				totalVenda += (itemVenda.getPrecoUnitario() * itemVenda.getQuantidade());
			}
			
			//cadastrando a venda
			Venda venda = new Venda();
			
			venda.setDataHora(new Date());
			venda.setValor(totalVenda);
			venda.setCliente(cliente);
			
			vendaRepository.save(venda);
			
			//cadastrando os itens da venda
			for(ItemVenda item : itensVenda) {
				
				item.setVenda(venda);
				itemVendaRepository.save(item);
			}
			
			response.setStatusCode(201);
			response.setMensagem("Venda realizada com sucesso.");
			response.setDataVenda(venda.getDataHora());
			response.setTotalVenda(venda.getValor());
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(response);	
		}
		catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);			
		}
	}
	
	@RequestMapping(value = ENDPOINT + "/{emailCliente}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<HistoricoVendasResponse> get(@PathVariable("emailCliente") String emailCliente){

		HistoricoVendasResponse response = new HistoricoVendasResponse();
		
		try {
			
			//consultar o cliente atraves do email
			Cliente cliente = clienteRepository.findByEmail(emailCliente);
		
			//verificar se o cliente é inválido
			if(cliente == null) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(null);
			}
			
			//dados do cliente
			response.setIdCliente(cliente.getIdCliente());
			response.setNomeCliente(cliente.getNome());
			response.setEmailCliente(cliente.getEmail());
			response.setVendas(new ArrayList<DetalhamentoVendasResponse>());
			
			//obtendo as vendas do cliente
			for(Venda venda : vendaRepository.findByCliente(cliente.getIdCliente())) {
				
				DetalhamentoVendasResponse det = new DetalhamentoVendasResponse();
				
				det.setIdVenda(venda.getIdVenda());
				det.setDataHora(venda.getDataHora());
				det.setValor(venda.getValor());
				det.setItensVenda(new ArrayList<ItemVendaResponse>());
				
				//obtendo os itens da venda
				for(ItemVenda itemVenda : itemVendaRepository.findByVenda(venda.getIdVenda())) {
					
					ItemVendaResponse item = new ItemVendaResponse();
					
					item.setIdItemVenda(itemVenda.getIdItemVenda());
					item.setQuantidade(itemVenda.getQuantidade());
					item.setPrecoUnitario(itemVenda.getPrecoUnitario());
					item.setTotal(itemVenda.getPrecoUnitario() * itemVenda.getQuantidade());
					item.setNomeProduto(itemVenda.getProduto().getNome());
					item.setDescricaoProduto(itemVenda.getProduto().getDescricao());
					item.setFotoProduto(itemVenda.getProduto().getFoto());
					
					det.getItensVenda().add(item);
				}
				
				response.getVendas().add(det);
			}
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
			
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}	
	
}