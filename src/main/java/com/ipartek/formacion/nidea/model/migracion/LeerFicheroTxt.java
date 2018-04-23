package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class LeerFicheroTxt {

	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace\\nidea_ALEX\\personas_little.txt";

	static HashMap<String, Integer> resumen;
	// El resumen se hace mediante un hasmap;

	public static void main(String[] args) throws SQLException {
		final String SQL = "INSERT INTO `usuario` (`nombre`, `password`, `email`, `id_rol`) VALUES (?, '123456', ?, ?);";
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";

		Connection con = null;
		PreparedStatement pst = null;

		resumen = new HashMap<String, Integer>();
		// Inicializo el HashMap
		resumen.put("lineasLeidas", 0);
		resumen.put("personasInsertadas", 0);
		resumen.put("lineasErroneas", 0);
		resumen.put("menoresEdad", 0);
		resumen.put("nombreEmailRepetido", 0);
		// resumen.put("emailRepetidos", 0);

		// for (Entry<String, Integer> map : resumen.entrySet()) {
		// // System.out.println(map.getKey() + " " + map.getValue());
		// }

		System.out.println("Comenzamos a leer ficheros");

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);

			// Indica que quiero tener el control sobre el commit
			con.setAutoCommit(false);

			String sCurrentLine;
			int i = 1;
			String campos[];
			String nombreCompleto;
			String email;
			int edad;
			int rol;

			while ((sCurrentLine = br.readLine()) != null) {

				System.out.println("Numero de linea: " + i);

				// Actualizo el contador de lineas leidas
				resumen.put("lineasLeidas", resumen.get("lineasLeidas") + 1);

				campos = sCurrentLine.split(",");

				nombreCompleto = campos[0] + " " + campos[1] + " " + campos[2];
				email = campos[4];
				edad = Integer.parseInt(campos[3]);

				// Preparo el rol dependiendo de la información
				switch (campos[6]) {
				case "JEFE":
					rol = 1;
					break;
				case "TRABAJADOR":
					rol = 2;
					break;
				default:
					rol = 2;
				}

				pst = con.prepareStatement(SQL);
				pst.setString(1, nombreCompleto);
				pst.setString(2, email);
				pst.setInt(3, rol);

				// Compruebo que contenga siete campos
				if (campos.length == 7) {
					if (edad >= 18) {
						try {
							if (1 == pst.executeUpdate()) {// Usuario insertado
								resumen.put("personasInsertadas", resumen.get("personasInsertadas") + 1);
								System.out.println("ok");
							}
						} catch (MySQLIntegrityConstraintViolationException mee) {
							System.out.println("Linea o email ya existente");
							resumen.put("nombreEmailRepetido", resumen.get("nombreEmailRepetido") + 1);
						}

					} else {
						resumen.put("menoresEdad", resumen.get("menoresEdad") + 1);
						// throw new Exception("El usuarios es menor de edad");
					}
				} else {// No tiene siete campos
					resumen.put("lineasErroneas", resumen.get("lineasErroneas") + 1);
					// throw new Exception("El número de campos no es 7");
				}
				i++;
			}

			// Si llega aquí es porque no hay errores
			con.commit();

		} catch (Exception e) {
			e.printStackTrace();

			// Si hay fallo rollback para dejar la BD como estaba
			con.rollback();

		} finally {

			// cerrar los recursos en orden inverso
			if (pst != null) {
				pst.close();
			}

			if (con != null) {
				con.close();
			}

			System.out.println("--------------------------------------------------------");
			System.out.println("--------------------------------------------------------");

			for (Entry<String, Integer> map : resumen.entrySet()) {
				System.out.println(map.getKey() + " " + map.getValue());
			}

		}

	}
}
