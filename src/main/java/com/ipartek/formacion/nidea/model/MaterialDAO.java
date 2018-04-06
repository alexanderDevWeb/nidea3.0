package com.ipartek.formacion.nidea.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Material;

public class MaterialDAO implements Persistible<Material> {

	private static MaterialDAO INSTANCE = null;

	// Private constructor para que no sepueda hacer new y crear N instancias
	private MaterialDAO() {
	}

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciación múltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MaterialDAO();
		}
	}

	// Creador synchronized para protegerse de posibles problemas multihilo
	public static MaterialDAO getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	/**
	 * Recupera todos los materiales de la BBDD ordenados por id descendente
	 * 
	 * @return ArrayList<Material> si no existen registros new ArrayList<Material>()
	 */
	@Override
	public ArrayList<Material> getAll(String search) {

		ArrayList<Material> lista = new ArrayList<Material>();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		// search = "";

		try {

			/*
			 * Esto utilizabamos al usar el DriverManager
			 * 
			 * Class.forName("com.mysql.jdbc.Driver"); final String URL =
			 * "jdbc:mysql://192.168.0.42/spoty?user=alumno&password=alumno"; con =
			 * DriverManager.getConnection(URL);
			 */

			// Ahora utilizaremos esto, por el pool de conexiones DATASOURCE
			con = ConnectionManager.getConnection();

			String sql = "SELECT id, nombre, precio FROM material WHERE NOMBRE LIKE '%" + search
					+ "%' ORDER BY ID DESC  LIMIT 500";

			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			Material m = null;
			while (rs.next()) {
				m = new Material();
				m.setId(rs.getInt("id"));
				m.setNombre(rs.getString("nombre"));
				m.setPrecio(rs.getFloat("precio"));
				lista.add(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}

				if (pst != null) {
					pst.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return lista;
	}

	/**
	 * Devuelvo el material con el id indicado
	 */
	@Override
	public Material getById(int id) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Material m = new Material();

		try {

			// Ahora utilizaremos esto, por el pool de conexiones DATASOURCE
			con = ConnectionManager.getConnection();

			String sql = "SELECT id, nombre, precio FROM material WHERE id=" + id + ";";

			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			// Necesario para que coja el primer valor
			rs.next();

			m.setId(rs.getInt("id"));
			m.setNombre(rs.getString("nombre"));
			m.setPrecio(rs.getFloat("precio"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}

				if (pst != null) {
					pst.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return m;
	}

	@Override
	public boolean save(Material pojo) {
		boolean resul = false;
		Connection con = null;
		PreparedStatement pst = null;
		try {

			con = ConnectionManager.getConnection();

			if (pojo.getId() == -1) { // Es un nuevo registro
				String sql = "INSERT INTO `material` (`nombre`,`precio`) VALUES(?,?);";
				pst = con.prepareStatement(sql);
				pst.setString(1, pojo.getNombre());
				pst.setFloat(2, pojo.getPrecio());
				System.out.println("Insertando");

			} else { // Es una modificación
				String sql = "UPDATE `material`  SET `nombre` = ?, `precio`= ? WHERE  `id`= ?;";
				pst = con.prepareStatement(sql);
				pst.setString(1, pojo.getNombre());
				pst.setFloat(2, pojo.getPrecio());
				pst.setInt(3, pojo.getId());
				System.out.println("Modificando");
			}

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {
				resul = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (pst != null) {
					pst.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	@Override
	public boolean delete(int id) {
		boolean resul = false;
		Connection con = null;
		PreparedStatement pst = null;
		try {

			con = ConnectionManager.getConnection();
			String sql = "DELETE FROM `material` WHERE  `id`= ?;";

			pst = con.prepareStatement(sql);
			pst.setInt(1, id);

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {
				resul = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (pst != null) {
					pst.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resul;

	}

}
