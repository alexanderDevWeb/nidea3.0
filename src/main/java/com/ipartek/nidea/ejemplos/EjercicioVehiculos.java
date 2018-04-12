package com.ipartek.nidea.ejemplos;

public class EjercicioVehiculos {

	public static void main(String[] args) {

		// No se puede instanciar la clase porque es abstracta
		// Vehiculo rayoMcQueen = new Vehiculo();
		// System.out.println(rayoMcQueen.toString());

		// Se puede usar al estar en el mismo paquete
		Vehiculo.dimeMatricula();

		System.out.println();
		System.out.println();

		VehiculoElectrico tesla = new VehiculoElectrico();
		System.out.println(tesla.toString());

		System.out.println("Tesla");
		System.out.println(tesla.getColor());
		// tesla.setPuertas(puertas);

		System.out.println();

		tesla.arrancar();
	}

}
