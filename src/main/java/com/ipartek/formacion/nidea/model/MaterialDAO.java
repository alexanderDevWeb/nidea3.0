package com.ipartek.formacion.nidea.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.nidea.util.Utilidades;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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

		// Necesario para utilizar la función mapper
		Material m = null;

		String sql = "SELECT m.id, m.nombre, m.precio, m.id_usuario, u.nombre as nombre_usuario ";
		sql += "FROM material m, usuario u WHERE u.id = m.id_usuario ";
		sql += "AND m.nombre LIKE ? ORDER BY ID DESC  LIMIT 500";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			/*
			 * Esto utilizabamos al usar el DriverManager
			 * 
			 * Class.forName("com.mysql.jdbc.Driver"); final String URL =
			 * "jdbc:mysql://192.168.0.42/spoty?user=alumno&password=alumno"; con =
			 * DriverManager.getConnection(URL);
			 */

			pst.setString(1, "%" + search + "%");
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					m = mapper(rs);
					lista.add(m);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<Material> getAllByUser(String search, Usuario user) {

		ArrayList<Material> lista = new ArrayList<Material>();

		// Necesario para utilizar la función mapper
		Material m = null;

		String sql = "SELECT m.id, m.nombre, m.precio, m.id_usuario, u.nombre as nombre_usuario "
				+ "FROM material m, usuario u " + "WHERE u.id = m.id_usuario " + "AND m.nombre LIKE ? "
				+ "AND m.id_usuario LIKE ? " + "ORDER BY ID DESC  LIMIT 500";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			/*
			 * Esto utilizabamos al usar el DriverManager
			 * 
			 * Class.forName("com.mysql.jdbc.Driver"); final String URL =
			 * "jdbc:mysql://192.168.0.42/spoty?user=alumno&password=alumno"; con =
			 * DriverManager.getConnection(URL);
			 */

			pst.setString(1, "%" + search + "%");
			pst.setInt(2, user.getId());
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					m = mapper(rs);
					lista.add(m);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Devuelvo el material con el id indicado
	 */
	@Override
	public Material getById(int id) {

		Material m = null;

		String sql = "SELECT m.id, m.nombre, m.precio, m.id_usuario, u.nombre as nombre_usuario "
				+ "FROM material m, usuario u WHERE m.id_usuario = u.id " + "AND m.id = ? ";

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
					m = mapper(rs);
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
		return m;
	}

	@Override
	public boolean save(Material pojo) throws MySQLIntegrityConstraintViolationException {
		boolean resul = false;
		String sql = "";

		// Formateo el nombre para quitarle los espacios
		pojo.setNombre(Utilidades.limpiarEspacios(pojo.getNombre()));

		// Dependiendo de si quiero guardar un registro nuevo o modificar uno
		// existente, la SQL será distinta
		if (pojo.getId() == -1) { // Es un nuevo registro
			sql = "INSERT INTO `material` (`nombre`,`precio`,`id_usuario`) VALUES(?,?,?);";
		} else {
			sql = "UPDATE `material`  SET `nombre` = ?, `precio`= ?, `id_usuario` = ? WHERE  `id`= ?;";
		}

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			if (pojo.getId() == -1) { // Es un nuevo registro
				pst.setString(1, pojo.getNombre());
				pst.setFloat(2, pojo.getPrecio());
				pst.setInt(3, pojo.getUser().getId());
				System.out.println("Insertando");

			} else { // Es una modificación
				pst.setString(1, pojo.getNombre());
				pst.setFloat(2, pojo.getPrecio());
				pst.setInt(3, pojo.getUser().getId());
				pst.setInt(4, pojo.getId());
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

	public boolean deleteChecked(int id, Usuario user) {
		boolean resul = false;
		String sql = "DELETE FROM `material` WHERE  `id`= ? AND `id_usuario` = ?";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			pst.setInt(1, id);
			pst.setInt(2, user.getId());

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
	public Material mapper(ResultSet rs) throws SQLException {
		Material m = null;
		if (rs != null) {
			m = new Material();
			m.setId(rs.getInt("id"));
			m.setNombre(rs.getString("nombre"));
			m.setPrecio(rs.getFloat("precio"));

			// Tengo que pasar un Usuario con el id de la base de datos
			// El siguiente código no es buena practica
			// daoUsuario = UsuarioDAO.getInstance();
			// m.setUser(daoUsuario.getById(rs.getInt("id_usuario")));

			Usuario user = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"));

			m.setUser(user);

		}
		return m;
	}

	// NO HA TERMINADO DE FUNCIONAR DE ESTA FORMA
	public int lastId() {
		int resul = -1;
		String sql = "SELECT LAST_INSERT_ID()";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {

					resul = rs.getInt("id");

					System.out.println(resul);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resul;
	}

	@Override
	public boolean delete(int id) {
		boolean resul = false;
		String sql = "DELETE FROM `material` WHERE  `id`= ?;";

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
}
