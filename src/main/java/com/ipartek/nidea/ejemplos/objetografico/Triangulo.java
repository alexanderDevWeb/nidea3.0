package com.ipartek.nidea.ejemplos.objetografico;

import com.ipartek.nidea.ejemplos.ObjetoGrafico;

public class Triangulo extends ObjetoGrafico {

	@Override
	public void dibujar() {
		System.out.println("Estoy Dibujando en Triangulo....");
	}

	@Override
	public void imprimir() {
		System.out.println("Estoy Imprimiendo en Triangulo....");
	}

	public Triangulo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
