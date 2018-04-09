package com.ipartek.formacion.nidea.pojo;

public class Coche implements AutoCloseable {

	public Coche() {
		super();
		System.out.println("Creamos coche");
	}

	public void conducir() {
		System.out.println("Brum BrumEstamos conduciendo");
	}

	// Ya viene implementeado para los resultsets, preapred statemtns... mediante
	// las librerías descrgadas de Maven
	@Override
	public void close() throws Exception {
		System.out.println("Paramos coche");
	}

	public static void main(String[] args) {

		// Si decalaramos un objeto que implemente la interfaz Autoclosable
		// dentro de los paréntesis de TRY, cuando llega al FINALLY se ejecuta
		// de forma automática su método close
		try (Coche c = new Coche()) {
			System.out.println("Empezamos programa");
			c.conducir();
		} catch (Exception e) {
			System.out.println("Tenemos una excepción");
		} finally {
			System.out.println("Finalizamos");
		}
	}

}
