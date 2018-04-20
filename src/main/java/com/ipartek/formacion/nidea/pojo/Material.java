package com.ipartek.formacion.nidea.pojo;

public class Material {

	private int id;
	private String nombre;
	private float precio;
	// Antes era un solo atributo
	private Usuario user;

	public Material() {
		super();
		this.id = -1;
		this.nombre = "";
		this.precio = 0f;
		this.user = new Usuario();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Material [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", user=" + user + "]";
	}

}
