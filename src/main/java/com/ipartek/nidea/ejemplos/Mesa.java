package com.ipartek.nidea.ejemplos;

public class Mesa implements Ordenable, Comparable<Object> {
	int numeroPatas;

	public Mesa() {
		this.numeroPatas = 0;
	}

	public int getNumeroPatas() {
		return numeroPatas;
	}

	public void setNumeroPatas(int numeroPatas) {
		this.numeroPatas = numeroPatas;
	}

	@Override
	public int getValor() {

		return numeroPatas;
	}

	@Override
	public String toString() {
		return "Mesa [numeroPatas=" + numeroPatas + "]";
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
