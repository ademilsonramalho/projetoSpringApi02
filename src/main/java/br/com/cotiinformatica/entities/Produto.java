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
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idProduto;

	@Column(length = 150, nullable = false)
	private String nome;

	@Column(nullable = false)
	private Double preco;

	@Column(nullable = false)
	private Integer quantidade;

	@Column(length = 250, nullable = false)
	private String foto;

	@Column(length = 500, nullable = false)
	private String descricao;

}



