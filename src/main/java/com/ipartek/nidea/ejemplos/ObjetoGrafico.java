package com.ipartek.nidea.ejemplos;

import com.ipartek.nidea.ejemplos.objetografico.Imprimible;

public abstract class ObjetoGrafico implements Imprimible {
	int x;
	int y;

	public void mover(int x, int y) {
		System.out.println("Me muevo a " + x + " y " + y + "....");
	}

	public abstract void dibujar();
}
