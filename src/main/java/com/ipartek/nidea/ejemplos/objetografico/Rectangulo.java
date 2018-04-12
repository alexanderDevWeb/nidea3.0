package com.ipartek.nidea.ejemplos.objetografico;

import com.ipartek.nidea.ejemplos.ObjetoGrafico;

public class Rectangulo extends ObjetoGrafico {

	@Override
	public void dibujar() {
		System.out.println("Estoy Dibujando en Rectangulo....");
	}

	@Override
	public void imprimir() {
		System.out.println("Estoy Imprimiendo en Rectangulo....");
	}

	public Rectangulo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
