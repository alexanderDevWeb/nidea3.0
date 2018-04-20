package com.ipartek.formacion.nidea.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.nidea.util.Utilidades;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class RolDAO implements Persistible<Rol> {

	private static RolDAO INSTANCE = null;

	// Private constructor para que no sepueda hacer new y crear N instancias
	private RolDAO() {
	}

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciación múltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RolDAO();
		}
	}

	public static RolDAO getInstance() {
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
	public ArrayList<Rol> getAll(String search) {

		ArrayList<Rol> lista = new ArrayList<Rol>();

		// Necesario para utilizar la función mapper
		Rol rol = null;

		String sql = "SELECT id, nombre FROM rol WHERE NOMBRE LIKE ? ORDER BY nombre LIMIT 500";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			pst.setString(1, "%" + search + "%");
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					rol = mapper(rs);
					lista.add(rol);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Devuelvo el Usuario con el nombreindicado
	 */
	public Rol getByNombre(String nombre, String password) {

		Rol rol = null;
		String sql = "SELECT id, nombre, id_rol FROM usuario WHERE nombre= ? and password = ?;";

		// Ahora utilizaremos esto, por el pool de conexiones DATASOURCE
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			pst.setString(1, nombre);
			pst.setString(2, password);

			try (ResultSet rs = pst.executeQuery()) {
				// Necesario aunque solo sea un valor
				while (rs.next()) {
					/*
					 * m = new Material(); m.setId(rs.getInt("id"));
					 * m.setNombre(rs.getString("nombre")); m.setPrecio(rs.getFloat("precio"));
					 */

					// mapper convierte unresultado de la BD a un objeto Material
					rol = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Antes de utilizar los Autoclosables había que cerrar los recursos en el
			// Ya no es necesario el finally si es solo para esto, podría quitarse

			/*
			 * try { if (rs != null) { rs.close(); }
			 * 
			 * if (pst != null) { pst.close(); }
			 * 
			 * if (con != null) { con.close(); } } catch (SQLException e) {
			 * e.printStackTrace(); }
			 */
		}
		return rol;
	}

	/**
	 * Devuelvo el rol con el id indicado
	 */
	@Override
	public Rol getById(int id) {

		Rol rol = null;
		String sql = "SELECT id, nombre FROM rol WHERE id= ? ;";

		// Ahora utilizaremos esto, por el pool de conexiones DATASOURCE
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				// Necesario aunque solo sea un valor
				while (rs.next()) {
					/*
					 * m = new Material(); m.setId(rs.getInt("id"));
					 * m.setNombre(rs.getString("nombre")); m.setPrecio(rs.getFloat("precio"));
					 */

					// mapper convierte unresultado de la BD a un objeto Material
					rol = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Antes de utilizar los Autoclosables había que cerrar los recursos en el
			// Ya no es necesario el finally si es solo para esto, podría quitarse

			/*
			 * try { if (rs != null) { rs.close(); }
			 * 
			 * if (pst != null) { pst.close(); }
			 * 
			 * if (con != null) { con.close(); } } catch (SQLException e) {
			 * e.printStackTrace(); }
			 */
		}
		return rol;
	}

	@Override
	public boolean save(Rol pojo) throws MySQLIntegrityConstraintViolationException {
		boolean resul = false;
		String sql = "";

		// Formateo el nombre para quitarle los espacios
		pojo.setNombre(Utilidades.limpiarEspacios(pojo.getNombre()));

		// Dependiendo de si quiero guardar un registro nuevo o modificar uno
		// existente, la SQL será distinta
		if (pojo.getId() == -1) { // Es un nuevo registro
			sql = "INSERT INTO `rol` (`nombre`) VALUES(?);";
		} else {
			sql = "UPDATE `rol`  SET `nombre` = ? WHERE  `id`= ?;";
		}

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			if (pojo.getId() == -1) { // Es un nuevo registro
				pst.setString(1, pojo.getNombre());
				System.out.println("Insertando");

			} else { // Es una modificación
				pst.setString(1, pojo.getNombre());
				pst.setInt(2, pojo.getId());
				System.out.println("Modificando");
			}

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {
				resul = true;
			}

			// Recuperar ID generado de forma automática
			try (ResultSet rs = pst.getGeneratedKeys()) {
				while (rs.next()) {
					pojo.setId(rs.getInt(1));
					System.out.println("Ultimo registro: " + rs.getInt(1));
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		catch (MySQLIntegrityConstraintViolationException e) {
			// e.printStackTrace();
			System.out.println("Primer throws de Unique exception");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}

	@Override
	public boolean delete(int id) {
		boolean resul = false;
		String sql = "DELETE FROM `rol` WHERE  `id`= ?;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			pst.setInt(1, id);

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {
				resul = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}

	@Override
	public Rol mapper(ResultSet rs) throws SQLException {
		Rol us = null;
		if (rs != null) {
			us = new Rol();
			us.setId(rs.getInt("id"));
			us.setNombre(rs.getString("nombre"));

		}
		return us;
	}

}
