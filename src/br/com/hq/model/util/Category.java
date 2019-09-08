/**
 * 
 */
package br.com.hq.model.util;

/**
 * @author HenriquePR
 *
 */
public enum Category {

	desconhecido ("desconhecido"),
	diversao ("diversao"),
	viagem ("viagem"),
	transporte ("transporte"),
	hospedagem ("hospedagem"),
	alimentacao ("alimentacao"),
	vestuario ("vestuario"),
	higiene ("higiene");
	
	
	private String name;
	
	Category (String name) {
		this.name = name;		
	}
	
	public String getName() {
		return this.name;
	}

}
