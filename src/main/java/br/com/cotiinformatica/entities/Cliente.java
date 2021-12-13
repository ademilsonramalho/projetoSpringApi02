package br.com.cotiinformatica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {
	// anotations
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idCliente;

	@Column(length = 150, nullable = false)
	private String nome;

	@Column(length = 15, nullable = false, unique = true)
	private String cpf;

	@Column(length = 15, nullable = false, unique = true)
	private String telefone;

	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@Column(length = 50, nullable = false)
	private String senha;

}





