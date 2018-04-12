package com.ipartek.formacion.nidea.pojo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Bebida {
	private int id;

	@NotNull
	@DecimalMin("0")
	private float precio;

	@NotNull
	@Size(min = 3, max = 45)
	private String nombre;

	public Bebida() {
		super();
		this.id = -1;
		this.nombre = "";
		this.precio = 0;
	}

	public Bebida(String nombre, float precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Bebida [id=" + id + ", nombre=" + nombre + ", precio=" + precio + "]";
	}

}
