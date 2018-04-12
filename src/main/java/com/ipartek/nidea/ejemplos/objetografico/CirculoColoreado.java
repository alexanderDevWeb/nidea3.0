package com.ipartek.nidea.ejemplos.objetografico;

public final class CirculoColoreado extends Circulo {
	String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "CirculoColoreado [color=" + color + "]";
	}

	public CirculoColoreado() {
		super();
		// TODO Auto-generated constructor stub
	}

}
