package br.com.cotiinformatica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Venda;

public interface IVendaRepository extends CrudRepository<Venda, Integer> {

	@Query("from Venda v where v.cliente.idCliente = :idCliente")
	List<Venda> findByCliente(@Param("idCliente") Integer idCliente);

}



