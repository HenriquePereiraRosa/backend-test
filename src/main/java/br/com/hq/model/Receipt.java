package br.com.hq.model;

public class Receipt {
	private String data;
	private String descricao;
	private String moeda;
	private String valor;
	private String categoria;
	
	
	public Receipt(String data, String descricao, 
			String moeda, String valor, String categoria) {
		this.data = data;
		this.descricao = descricao;
		this.moeda = moeda;
		this.valor = valor;
		this.categoria = categoria;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
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


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}