package com.ipartek.formacion.nidea.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.nidea.util.Utilidades;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class UsuarioDAO implements Persistible<Usuario> {

	private static UsuarioDAO INSTANCE = null;

	// Private constructor para que no sepueda hacer new y crear N instancias
	private UsuarioDAO() {
	}

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciación múltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UsuarioDAO();
		}
	}

	public static UsuarioDAO getInstance() {
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
	public ArrayList<Usuario> getAll(String search) {

		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		// Necesario para utilizar la función mapper
		Usuario us = null;

		// String sql = "SELECT id, nombre, id_rol FROM usuario WHERE NOMBRE LIKE ? ORDER BY ID DESC LIMIT 500";
		String sql = "SELECT u.id, u.nombre, id_rol, r.nombre as rol FROM usuario u, rol r WHERE id_rol = r.id";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					us = mapper(rs);
					lista.add(us);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Para la API
	public ArrayList<Usuario> getAllByName(String search) {

		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		// Necesario para utilizar la función mapper
		Usuario us = null;

		// String sql = "SELECT id, nombre, id_rol FROM usuario WHERE NOMBRE LIKE ? ORDER BY ID DESC LIMIT 500";
		// String sql = "SELECT u.id, u.nombre, id_rol, r.nombre as rol FROM usuario u, rol r WHERE id_rol = r.id";

		String sql = "SELECT u.id, u.nombre, id_rol, r.nombre as rol  ";
		sql += "FROM usuario u, rol r WHERE id_rol = r.id AND ";
		sql += "u.nombre LIKE ? ORDER BY u.id DESC LIMIT 500";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
			pst.setString(1, "%" + search + "%");
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					us = mapper(rs);
					lista.add(us);
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

	public Usuario check(String nombre, String password) {

		Usuario us = null;
		String sql = "SELECT u.id, u.nombre, id_rol, r.nombre as rol FROM usuario u,";
		sql += "rol r WHERE id_rol = r.id AND u.nombre = ? and password = ?;";

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
					us = mapper(rs);
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
		return us;
	}

	/**
	 * Devuelvo el material con el id indicado
	 */
	@Override
	public Usuario getById(int id) {

		Usuario us = null;
		String sql = "SELECT u.id, u.nombre, id_rol, r.nombre as rol FROM usuario u, ";
		sql += "rol r WHERE u.id = ?;";

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
					us = mapper(rs);
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
		return us;
	}

	/*
	 * @Override
	 * public boolean save(Usuario pojo) throws MySQLIntegrityConstraintViolationException {
	 * boolean resul = false;
	 * String sql = "";
	 * 
	 * // Formateo el nombre para quitarle los espacios
	 * pojo.setNombre(Utilidades.limpiarEspacios(pojo.getNombre()));
	 * 
	 * // Dependiendo de si quiero guardar un registro nuevo o modificar uno
	 * // existente, la SQL será distinta
	 * if (pojo.getId() == -1) { // Es un nuevo registro
	 * sql = "INSERT INTO `material` (`nombre`,`precio`) VALUES(?,?);";
	 * } else {
	 * sql = "UPDATE `material`  SET `nombre` = ?, `precio`= ? WHERE  `id`= ?;";
	 * }
	 * 
	 * try (Connection con = ConnectionManager.getConnection();
	 * PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	 * 
	 * if (pojo.getId() == -1) { // Es un nuevo registro
	 * pst.setString(1, pojo.getNombre());
	 * pst.setFloat(2, pojo.getPrecio());
	 * System.out.println("Insertando");
	 * 
	 * } else { // Es una modificación
	 * pst.setString(1, pojo.getNombre());
	 * pst.setFloat(2, pojo.getPrecio());
	 * pst.setInt(3, pojo.getId());
	 * System.out.println("Modificando");
	 * }
	 * 
	 * int affectedRows = pst.executeUpdate();
	 * 
	 * if (affectedRows == 1) {
	 * resul = true;
	 * }
	 * 
	 * // Recuperar ID generado de forma automática
	 * try (ResultSet rs = pst.getGeneratedKeys()) {
	 * while (rs.next()) {
	 * pojo.setId(rs.getInt(1));
	 * System.out.println("Ultimo registro: " + rs.getInt(1));
	 * }
	 * 
	 * } catch (Exception e) {
	 * // TODO: handle exception
	 * }
	 * }
	 * 
	 * catch (MySQLIntegrityConstraintViolationException e) {
	 * // e.printStackTrace();
	 * System.out.println("Primer throws de Unique exception");
	 * throw e;
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * }
	 * return resul;
	 * }
	 * 
	 * @Override
	 * public boolean delete(int id) {
	 * boolean resul = false;
	 * String sql = "DELETE FROM `material` WHERE  `id`= ?;";
	 * 
	 * try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
	 * 
	 * pst.setInt(1, id);
	 * 
	 * int affectedRows = pst.executeUpdate();
	 * 
	 * if (affectedRows == 1) {
	 * resul = true;
	 * }
	 * 
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * }
	 * return resul;
	 * }
	 */

	@Override
	public boolean save(Usuario pojo) throws MySQLIntegrityConstraintViolationException {
		boolean resul = false;
		String sql = "";

		// Formateo el nombre para quitarle los espacios
		pojo.setNombre(Utilidades.limpiarEspacios(pojo.getNombre()));

		// Dependiendo de si quiero guardar un registro nuevo o modificar uno
		// existente, la SQL será distinta
		if (pojo.getId() == -1) { // Es un nuevo registro
			sql = "INSERT INTO `usuario` (`nombre`, `password`, `id_rol`) VALUES(?,'',?);";
		} else {
			sql = "UPDATE `usuario`  SET `nombre` = ?,  `id_rol` = ? WHERE  `id`= ?;";
		}

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			if (pojo.getId() == -1) { // Es un nuevo registro
				pst.setString(1, pojo.getNombre());
				pst.setInt(2, pojo.getRol().getId());
				System.out.println("Insertando");

			} else { // Es una modificación
				pst.setString(1, pojo.getNombre());
				pst.setInt(2, pojo.getRol().getId());
				pst.setInt(3, pojo.getId());
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
				e.printStackTrace();
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
	public Usuario mapper(ResultSet rs) throws SQLException {
		Usuario us = null;
		if (rs != null) {
			us = new Usuario();
			us.setId(rs.getInt("id"));
			us.setNombre(rs.getString("nombre"));
			// us.getRol().setId(rs.getInt("id_rol"));
			us.setRol(new Rol(rs.getInt("id_rol"), rs.getString("rol")));
		}
		return us;
	}

	@Override
	public boolean delete(int id) {
		boolean resul = false;
		String sql = "DELETE FROM `usuario` WHERE  `id`= ?;";

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

	public ArrayList<Usuario> checkNameRegistro(String nombre) {

		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		// Necesario para utilizar la función mapper
		Usuario us = null;

		String sql = "SELECT u.id, u.nombre, id_rol, r.nombre as rol  ";
		sql += "FROM usuario u, rol r WHERE id_rol = r.id AND ";
		sql += "u.nombre = ? ORDER BY u.id DESC LIMIT 500";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
			pst.setString(1, nombre);
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					us = mapper(rs);
					lista.add(us);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public boolean registrar(Usuario pojo) throws MySQLIntegrityConstraintViolationException {
		boolean resul = false;
		String sql = "";

		// Formateo el nombre para quitarle los espacios
		pojo.setNombre(Utilidades.limpiarEspacios(pojo.getNombre()));

		sql = "INSERT INTO `usuario` (`nombre`, `password`, `email`, `id_rol`) VALUES(?, ?, ? ,?);";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getPassword());
			pst.setString(3, pojo.getNombre() + "@" + pojo.getNombre() + ".com");
			pst.setInt(4, 2);
			System.out.println("Insertando");

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {
				resul = true;
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			System.out.println("Registro repetido aunque está controlado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}

}
