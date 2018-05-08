package com.ipartek.formacion.nidea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcedureCallTest {

	static String nombre = "periko"; // Usuario a insertar
	static String email = "periko@gmail.com";
	static int lastId = 0; // Id provisional. Será el ID del usuario creado

	/**
	 * Antes de ejecutar los tests, inserta el usuario definido de forma global
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		// Antes de comenzar con los tests
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL = "{call pa_create_user(?,?,?)}";

			Connection con = DriverManager.getConnection(URL);

			CallableStatement cs = con.prepareCall(SQL);

			// Resgitramos parámetros entrada y salida
			cs.setString(1, nombre);
			cs.setString(2, email);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);

			// Ejecutramos procediemitno
			cs.execute();

			// REcogemos los parámteros
			lastId = cs.getInt(3);
			System.out.println(cs.getInt(3));

			assertTrue(true);

			// Deberiamos haber insertado un usuario para comprobar
			// que lo encontramos mediante el PA
			assertTrue(true);

		} catch (Exception e) {

			e.printStackTrace();
			fail("Error: " + e.getMessage());
		}
	}

	/**
	 * Antes de finalizar los tests, elimina el usuario global creado
	 */
	@AfterClass
	public static void tearDownAfterClass() {
		// Después de terminar con los test
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL = "{call pa_delete_user(?)}";

			Connection con = DriverManager.getConnection(URL);

			CallableStatement cs = con.prepareCall(SQL);

			// Resgitramos parámetros entrada y salida
			cs.setInt(1, lastId);

			// Ejecutramos procediemitno
			cs.execute();

			// Deberiamos haber insertado un usuario para comprobar
			// que lo encontramos mediante el PA
			assertTrue(true);

		} catch (Exception e) {

			e.printStackTrace();
			fail("Error: " + e.getMessage());
		}
	}

	/**
	 * Comprueba que el usuario global se ha creado correctamente
	 */
	@Test
	public void testPageUserById() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL = "{call pa_get_user_by_id(?,?,?)}";

			Connection con = DriverManager.getConnection(URL);

			CallableStatement cs = con.prepareCall(SQL);

			// Resgitramos parámetros entrada y salida
			cs.setInt(1, lastId);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);

			// Ejecutramos procediemitno
			cs.execute();

			// REcogemos los parámteros
			assertEquals(nombre, cs.getString(2));
			assertEquals(email, cs.getString(3));

			// Si llega aquí, el usuario se ha creado correctamente
			// que lo encontramos mediante el PA
			assertTrue(true);

		} catch (Exception e) {

			e.printStackTrace();
			fail("Error: " + e.getMessage());
		}

	}

}
