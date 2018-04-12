package com.ipartek.nidea.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.ipartek.nidea.ejemplos.Ordenable;

public class Utilidades implements Comparator {

	/**
	 * 
	 * Metodo estático para poder usarse desde la propia clase,
	 * sin tener que instanciar un objeto
	 * 
	 * Limpia los caracteres vacíos de una cadena
	 * Sustituye todos los espacios en blanco por uno único
	 * ej: " hola____que____hace " => "hola que hace"
	 * 
	 * En caso de null retorna cadean vacía
	 * 
	 * @param cadena
	 * @return
	 */
	public static String limpiarEspacios(String cadena) {
		String resul;
		if (cadena == null) {
			resul = "";
		} else {
			resul = cadena.trim().replaceAll("\\s+", " ");
		}
		return resul;
	}

	/**
	 * Ordenar una coleccion con el algoritmo de bubble sort
	 * Ordena de menor a mayor basandose en el metodo getValor
	 * de la interfaz Ordenable
	 * 
	 * @param <T>
	 * 
	 * @see com.ipartek.formacion.nidea.ejemplos.Ordenable
	 * 
	 * @param coleccion
	 *            List<Ordenable> coleccion con los elementos a ordenar
	 * 
	 * 
	 * @return List<Ordenable> En caso de null retornamos una lista vacía
	 * 
	 */
	public static <T> List<Ordenable> bubbleSort(List<Ordenable> coleccion) {
		List<Ordenable> resul = new ArrayList<Ordenable>();

		if (coleccion != null) { // Si es null devuelve una lista vacía

			resul = coleccion;
		}
		return resul;
	}

	public static <T> List<Ordenable> sort(List<Ordenable> coleccion) {
		List<Ordenable> resul = new ArrayList<Ordenable>();

		if (coleccion != null) { // Si es null devuelve una lista vacía
			// TODO implementar metodo bubbleSort

			resul = coleccion;
		}
		return resul;
	}

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
