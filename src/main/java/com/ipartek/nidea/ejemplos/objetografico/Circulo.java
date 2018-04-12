package com.ipartek.nidea.ejemplos.objetografico;

import com.ipartek.nidea.ejemplos.ObjetoGrafico;

public class Circulo extends ObjetoGrafico {

	@Override
	public void dibujar() {
		System.out.println("Estoy Dibujando en Circulo....");
	}

	@Override
	public void imprimir() {
		System.out.println("Estoy Imprimiendo en Circulo....");

	}

	public Circulo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
