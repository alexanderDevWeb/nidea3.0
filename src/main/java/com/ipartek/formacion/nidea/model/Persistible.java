package com.ipartek.formacion.nidea.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface Persistible<P> {

	/**
	 * Listado de una tabla de la base de datos ordenado por id descendente y
	 * limitado a 500
	 * 
	 * @return Collection
	 */
	public ArrayList<P> getAll(String search);

	/**
	 * Obtenemos el detalle de un registro
	 * 
	 * @param id
	 *            identificador
	 * @return Registro si existe, null en caso contrario
	 */
	public P getById(int id);

	/**
	 * Guardamos un registro en la base de datos. Si el id es == -1 creamos Si > 1
	 * modificamos
	 * 
	 * @param pojo
	 * @return
	 * @throws MySQLIntegrityConstraintViolationException
	 */
	public boolean save(P pojo) throws MySQLIntegrityConstraintViolationException;

	/**
	 * Eliminamos un registro por su identificador
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(int id);

	/**
	 * Nos mapea un Resutlado de la BD a un Pojo
	 * 
	 * @param rs
	 *            ResultSet 1 Registro de la consulta
	 * @return Pojo con los valores del ResultSet o null si no hay valores
	 * @throws SQLException
	 */
	public P mapper(ResultSet rs) throws SQLException;
}
