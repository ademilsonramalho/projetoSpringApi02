package br.com.cotiinformatica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.ItemVenda;

public interface IItemVendaRepository extends CrudRepository<ItemVenda, Integer> {

	// Consulta para obter todos os itens de venda de uma determinada venda
	@Query("from ItemVenda i where i.venda.idVenda = :param")
	List<ItemVenda> findByVenda(@Param("param") Integer idVenda);

}





