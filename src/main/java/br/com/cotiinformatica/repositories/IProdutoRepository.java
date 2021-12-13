package br.com.cotiinformatica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.cotiinformatica.entities.Produto;

public interface IProdutoRepository extends CrudRepository<Produto, Integer> {
		
		@Query("from Produto p  where p.nome like :nome")
		List<Produto> findAll(String nome);
		
}
