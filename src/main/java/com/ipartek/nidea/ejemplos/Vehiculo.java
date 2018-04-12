package com.ipartek.nidea.ejemplos;

public abstract class Vehiculo {
	private int puertas;
	private String color;

	public Vehiculo() {
		super(); // java.lang.Object
		this.puertas = 3;
		this.color = "blanco";
		System.out.println("Instanciado Vehículo Nuevo");
	}

	public int getPuertas() {
		return puertas;
	}

	public void setPuertas(int puertas) {
		this.puertas = puertas;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public abstract void arrancar(); // Hace que toda la clase tenga que ser abstracta

	public void encenderLuces() {
		System.out.println("Luces encedidas");
	}

	protected static void dimeMatricula() {
		// metodo tonto para probar package
		System.out.println("Matricula");
	}

	@Override
	public String toString() {
		return "Vehiculo [puertas=" + puertas + ", color=" + color + "]";
	}

}
