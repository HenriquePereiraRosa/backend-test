package br.com.hq.model;

import java.time.MonthDay;
// import java.util.Optional;

import br.com.hq.model.util.Category;

public class Operation {

	private MonthDay data;
	private String descricao;
	private String moeda;
	private Float valor;
	private Category categoria;
	
	
	public Operation(MonthDay monthDay, String descricao,
					 String moeda, Float valor, String categoria) {
		this.data = monthDay;
		this.descricao = descricao;
		this.moeda = moeda;
		this.valor = valor;
		this.setCategoria(this.chooseCategory(categoria));
	}


	public MonthDay getData() {
		return data;
		
	}


	public void setData(MonthDay data) {
		this.data = data;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getMoeda() {
		return moeda;
	}


	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}


	public Float getValor() {
		return valor;
	}


	public void setValor(Float valor) {
		this.valor = valor;
	}


	public Category getCategoria() {
		return categoria;
	}


	public void setCategoria(Category categoria) {
		this.categoria = categoria;
	}
	

	public Category chooseCategory(String categoria) {
		
		try {
			categoria.isEmpty();
		} catch (NullPointerException e) {
			System.out.println("Categoria nula");
			return Category.desconhecido;
		}

		categoria = categoria
				.replace(" ", "").replace("ã", "a")
				.replace("ç", "c")
				.replace("é", "e")
				.toLowerCase();

		if(categoria.contains(Category.diversao.getName())) {
			return Category.diversao;
		}

		if(categoria.contains(Category.viagem.getName())) {
			return Category.viagem;
		}

		if(categoria.contains(Category.transporte.getName())) {
			return Category.transporte;
		}

		if(categoria.contains(Category.hospedagem.getName())) {
			return Category.hospedagem;
		}

		if(categoria.contains(Category.alimentacao.getName())) {
			return Category.alimentacao;
		}

		if(categoria.contains(Category.vestuario.getName())) {
			return Category.vestuario;
		}

		if(categoria.contains(Category.higiene.getName())) {
			return Category.higiene;
		}
		return Category.desconhecido;
	}


	
}