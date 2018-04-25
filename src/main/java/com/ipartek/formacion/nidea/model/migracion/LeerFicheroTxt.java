package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class LeerFicheroTxt {

	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace\\nidea_ALEX\\personas.txt";
	private final static String SQL = "INSERT INTO `usuario` (`nombre`, `password`, `email`, `id_rol`) VALUES (?, '123456', ?, ?);";
	private final static String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";

	public static void main(String[] args) throws SQLException {

		Connection con = null;
		PreparedStatement pst = null;

		// // El resumen se hace mediante un hasmap;
		HashMap<String, Integer> resumen = new HashMap<String, Integer>();

		// Inicializo el HashMap
		resumen.put("lineasLeidas", 0);
		resumen.put("personasInsertadas", 0);
		resumen.put("lineasErroneas", 0);
		resumen.put("menoresEdad", 0);
		resumen.put("nombreEmailRepetido", 0);

		ArrayList<String> usuariosError = new ArrayList<String>();

		System.out.println("Comenzamos a leer ficheros");

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);

			// Indica que quiero tener el control sobre el commit
			con.setAutoCommit(false);

			String sCurrentLine;
			// int i = 1;
			String campos[];
			String nombreCompleto;
			String email;
			int edad;
			int rol;

			while ((sCurrentLine = br.readLine()) != null) {

				// System.out.println("Numero de linea: " + i);

				// Actualizo el contador de lineas leidas
				resumen.put("lineasLeidas", resumen.get("lineasLeidas") + 1);

				// Convierto la respuesta en un array
				campos = sCurrentLine.split(",");

				// Compruebo que contenga siete campos y sea mayor de edad
				if (campos.length == 7) {
					nombreCompleto = campos[0] + " " + campos[1] + " " + campos[2];
					email = campos[4];
					edad = Integer.parseInt(campos[3]);

					if (edad >= 18) { // Compruebo que es mayor de 18

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

						try {
							if (1 == pst.executeUpdate()) {// Usuario insertado
								resumen.put("personasInsertadas", resumen.get("personasInsertadas") + 1);
							}
						} catch (MysqlDataTruncation dt) {// No guarda por longitud

							// System.out.println("Nombre a truncar: " + nombreCompleto);

							// Trunco el nombre y lo guardo
							nombreCompleto = nombreCompleto.substring(0, 44);
							System.out.println("Nombre truncado: " + nombreCompleto);

							// Vuelvo a configurar la consulta
							pst = con.prepareStatement(SQL);
							pst.setString(1, nombreCompleto);
							pst.setString(2, email);
							pst.setInt(3, rol);

							try {
								if (1 == pst.executeUpdate()) {// Usuario insertado
									resumen.put("personasInsertadas", resumen.get("personasInsertadas") + 1);
									System.out.println("Insertado tras truncar");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						} catch (MySQLIntegrityConstraintViolationException mee) {
							resumen.put("nombreEmailRepetido", resumen.get("nombreEmailRepetido") + 1);
							usuariosError.add(nombreCompleto);
						}

					} else {
						resumen.put("menoresEdad", resumen.get("menoresEdad") + 1);
					}
				} else {// No tiene siete campos
					resumen.put("lineasErroneas", resumen.get("lineasErroneas") + 1);
				}

				// Actualizo el contador
				// i++;
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
			System.out.println("----------------------- RESUMEN ------------------------");
			System.out.println("--------------------------------------------------------");

			// Muestro la información del HashMAp
			for (Entry<String, Integer> map : resumen.entrySet()) {
				System.out.println(map.getKey() + " " + map.getValue());
			}

			System.out.println("--------------------------------------------------------");
			System.out.println("------------ Usuarios que provocan error ---------------");
			System.out.println("--------------------------------------------------------");

			// Muestro los usuarios que han provocado errores
			for (String user : usuariosError) {
				System.out.println(user);
			}
		}
	}
}
