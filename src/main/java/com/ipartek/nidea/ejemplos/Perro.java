package com.ipartek.nidea.ejemplos;

public class Perro implements Ordenable {
	int numeroVacunas;

	public Perro() {
		this.numeroVacunas = 0;
	}

	public int getNumeroVacunas() {
		return numeroVacunas;
	}

	public void setNumeroVacunas(int numeroVacunas) {
		this.numeroVacunas = numeroVacunas;
	}

	@Override
	public int getValor() {
		return numeroVacunas;
	}

	@Override
	public String toString() {
		return "Perro [numeroVacunas=" + numeroVacunas + "]";
	}

}
