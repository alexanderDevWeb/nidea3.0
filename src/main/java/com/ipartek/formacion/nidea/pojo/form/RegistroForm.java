package com.ipartek.formacion.nidea.pojo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistroForm {

	@NotNull
	@Size(min = 3, max = 45)
	private String nombre;

	@NotNull
	@Size(min = 6, max = 45)
	private String password;

	@NotNull
	@Size(min = 6, max = 45)
	private String cPassword;

	public RegistroForm() {
		super();
		this.nombre = "";
		this.password = "";
		this.cPassword = "";
	}

	public RegistroForm(String nombre, String password, String cPassword) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.cPassword = cPassword;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}

	@Override
	public String toString() {
		return "RegistroForm [nombre=" + nombre + ", password=" + password + ", cPassword=" + cPassword + "]";
	}

}
