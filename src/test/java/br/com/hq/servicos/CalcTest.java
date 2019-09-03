package br.com.hq.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.hq.exceptions.DivisionByZeroException;
import br.com.hq.servicos.Calc;

public class CalcTest {
	
	private Calc calc;
	
	@Before
	public void setup(){
		calc = new Calc();
	}

	@Test
	public void deveSomarDoisValores(){
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int resultado = calc.add(a, b);
		
		//verificacao
		Assert.assertEquals(8, resultado);
		
	}
	
	@Test
	public void deveSubtrairDoisValores(){
		//cenario
		int a = 8;
		int b = 5;
		
		//acao
		int resultado = calc.sub(a, b);
		
		//verificacao
		Assert.assertEquals(3, resultado);
		
	}
	
	@Test
	public void deveDividirDoisValores() throws DivisionByZeroException{
		//cenario
		int a = 6;
		int b = 3;
		
		//acao
		int resultado = calc.div(a, b);
		
		//verificacao
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = DivisionByZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws DivisionByZeroException{
		int a = 10;
		int b = 0;
		
		calc.div(a, b);
	}
}
