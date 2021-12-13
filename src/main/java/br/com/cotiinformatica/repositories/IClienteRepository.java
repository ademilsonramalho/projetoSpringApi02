package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Cliente;

public interface IClienteRepository extends CrudRepository<Cliente, Integer> {

	@Query("from Cliente c where c.cpf = :cpf")
	Cliente findByCpf(@Param("cpf") String cpf);

	@Query("from Cliente c where c.telefone = :telefone")
	Cliente findByTelefone(@Param("telefone") String telefone);

	@Query("from Cliente c where c.email = :email")
	Cliente findByEmail(@Param("email") String email);

	@Query("from Cliente c where c.email = :email and c.senha = :senha")
	Cliente findByEmailSenha(@Param("email") String email, @Param("senha") String senha);

}




