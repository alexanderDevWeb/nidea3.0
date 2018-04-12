package com.ipartek.formacion.nidea.ejemplos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ArrayListTest {

	static ArrayList<String> arr = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		arr = new ArrayList<String>();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		arr = null;
	}

	@Before
	public void setUp() throws Exception {
		arr.add("España");
		arr.add("Francia");
		arr.add("Portugal");
	}

	@After
	public void tearDown() throws Exception {
		arr = null;
	}

	@Test
	public void test() {
		assertEquals("Primera posición...", "España", arr.get(0));
	}

	@Test
	public void anadirPais() {
		arr.add("Italia");
		assertEquals("Anadir elemento...", "Italia", arr.get(3));
	}

}
