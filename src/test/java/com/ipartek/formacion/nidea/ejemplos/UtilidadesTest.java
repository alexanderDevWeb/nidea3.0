package com.ipartek.formacion.nidea.ejemplos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ipartek.nidea.util.Utilidades;

public class UtilidadesTest {

	@Test
	public void testLimpiarEspacios() {
		assertEquals("hola que hase", Utilidades.limpiarEspacios(" hola     que     hase "));
		assertEquals("", Utilidades.limpiarEspacios(null));
	}

}
