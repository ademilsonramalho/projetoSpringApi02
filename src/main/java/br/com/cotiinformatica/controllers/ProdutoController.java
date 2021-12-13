package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.IProdutoRepository;
import br.com.cotiinformatica.requests.ProdutoResponse;

@Controller
@Transactional
public class ProdutoController {

	private static final String ENDPOINT = "/api/v1/produtos";

	@Autowired
	private IProdutoRepository produtoRepository;

	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ProdutoResponse>> getAll() {

		try {

			// consultar os produtos na base de dados
			List<Produto> produtos = (List<Produto>) produtoRepository.findAll();

			List<ProdutoResponse> response = new ArrayList<ProdutoResponse>();

			for (Produto produto : produtos) {

				ProdutoResponse item = new ProdutoResponse();

				item.setIdProduto(produto.getIdProduto());
				item.setNome(produto.getNome());
				item.setPreco(produto.getPreco());
				item.setQuantidade(produto.getQuantidade());
				item.setDescricao(produto.getDescricao());
				item.setFoto(produto.getFoto());

				response.add(item);
			}

			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ProdutoResponse> getById(@PathVariable("id") Integer id) {

		try {

			// consultar os dados do produto através do ID..
			Optional<Produto> produto = produtoRepository.findById(id);

			// verificar se o produto foi encontrado
			if (produto.isPresent()) {

				Produto item = produto.get();

				ProdutoResponse response = new ProdutoResponse();

				response.setIdProduto(item.getIdProduto());
				response.setNome(item.getNome());
				response.setPreco(item.getPreco());
				response.setQuantidade(item.getQuantidade());
				response.setDescricao(item.getDescricao());
				response.setFoto(item.getFoto());

				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
