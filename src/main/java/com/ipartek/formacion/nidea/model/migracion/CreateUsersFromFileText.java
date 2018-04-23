package com.ipartek.formacion.nidea.model.migracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.NamingException;

public class CreateUsersFromFileText {

	public static void main(String[] args) throws SQLException, NamingException {
		System.out.println("Crear usuarios desde un fichero de texto");
		final String SQL = "INSERT INTO `usuario` (`nombre`, `password`, `id_rol`) VALUES (?, '123456', '2');";
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";

		Connection con = null;
		PreparedStatement pst = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);

			// Indica que quiero tener el control sobre el commit
			con.setAutoCommit(false);

			for (int i = 0; i < 5; i++) {
				pst = con.prepareStatement(SQL);
				pst.setString(1, "usuario" + i);

				if (1 == pst.executeUpdate()) {
					System.out.println("Usuario insertado");

					if (i == 2) {
						throw new Exception("Lanzo adrede para probar rollback");
					}
				} else {
					System.out.println("****" + i + "Error al isertar usuario");
				}
			}

			// comitar cambios al terminar el proceso
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
		}

	}

}
