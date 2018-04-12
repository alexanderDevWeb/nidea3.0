package com.ipartek.nidea.ejemplos;

public class VehiculoElectrico extends Vehiculo {

	private float potencia; // KW

	public VehiculoElectrico() {
		super();
		this.potencia = 0;
		System.out.println("Instanciado Vehículo Eléctrico");
	}

	public VehiculoElectrico(float potencia) {
		this(); // Por defecto escribe super()
		this.potencia = potencia;
	}

	public float getPotencia() {
		return potencia;
	}

	public void setPotencia(float potencia) {
		this.potencia = potencia;
	}

	@Override
	public void arrancar() {
		System.out.println("Pulsar botón encendido");
		Vehiculo.dimeMatricula();
	}

	@Override
	public String toString() {
		return super.toString() + "VehiculoElectrico [potencia=" + potencia + "]";
	}

}
