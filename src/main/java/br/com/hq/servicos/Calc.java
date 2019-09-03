package br.com.hq.servicos;

import br.com.hq.exceptions.DivisionByZeroException;

public class Calc {

	public int add(int a, int b) {
		return a + b;
	}

	public int sub(int a, int b) {
		return a - b;
	}

	public int div(int a, int b) throws DivisionByZeroException {
		if(b == 0) {
			throw new DivisionByZeroException();
		}
		return a / b;
	}

}
