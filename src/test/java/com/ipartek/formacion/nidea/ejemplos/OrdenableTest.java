package com.ipartek.formacion.nidea.ejemplos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.nidea.ejemplos.ComparatorOrdenables;
import com.ipartek.nidea.ejemplos.Mesa;
import com.ipartek.nidea.ejemplos.Ordenable;
import com.ipartek.nidea.ejemplos.Perro;
import com.ipartek.nidea.util.Utilidades;

public class OrdenableTest {

	static ArrayList<Ordenable> coleccion;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		coleccion = new ArrayList<Ordenable>();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		coleccion = null;
	}

	@Before
	public void setUp() throws Exception {

		Mesa m1 = new Mesa();
		m1.setNumeroPatas(3);

		Mesa m2 = new Mesa();
		m2.setNumeroPatas(9);

		Perro p1 = new Perro();
		p1.setNumeroVacunas(0);

		Perro p2 = new Perro();
		p2.setNumeroVacunas(1);

		coleccion.add(m1);
		coleccion.add(m2);
		coleccion.add(p1);
		coleccion.add(p2);

		// System.out.println(m2.getClass().getSimpleName());
	}

	@After
	public void tearDown() throws Exception {
		coleccion.clear();
	}

	@Test
	public void bubbleSort() {
		// TODO
		// Crear mesas y perros y meterlos al arraylist
		// Ordenar el array con Sort

		Utilidades.bubbleSort(coleccion);

		System.out.println(coleccion);

		for (Ordenable element : coleccion) {
			System.out.println(element.toString());
		}

		assertEquals(0, coleccion.get(0).getValor());
		assertEquals(1, coleccion.get(1).getValor());
		assertEquals(3, coleccion.get(2).getValor());
		assertEquals(9, coleccion.get(3).getValor());

	}

	@Test
	public void testCollectionSort() {

		// 0 son iguales
		// <0 el primer objeto es menor
		// >0 el primer objeto es mayor

		Collections.sort(coleccion, new ComparatorOrdenables()); // Comparator obliga a crear una clase

		assertEquals(0, coleccion.get(0).getValor());
		assertEquals(1, coleccion.get(1).getValor());
		assertEquals(3, coleccion.get(2).getValor());
		assertEquals(9, coleccion.get(3).getValor());

		Collections.reverse(coleccion); // Comparator obliga a crear una clase

		assertEquals(9, coleccion.get(0).getValor());
		assertEquals(3, coleccion.get(1).getValor());
		assertEquals(1, coleccion.get(2).getValor());
		assertEquals(0, coleccion.get(3).getValor());

	}

}
