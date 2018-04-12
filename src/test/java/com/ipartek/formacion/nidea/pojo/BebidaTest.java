package com.ipartek.formacion.nidea.pojo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

public class BebidaTest {

	@Test
	public void testConstraintsBebida() {

		try {

			// Crear Factoria y Validador
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();

			// Array que contendrá 3 pruebas
			ArrayList<Bebida> bebidas = new ArrayList<Bebida>();

			Bebida beb = new Bebida("aa", -0.1f);
			Bebida beb2 = new Bebida("aaa", -0.1f);
			Bebida beb3 = new Bebida("aaa", 0.1f);

			// Añado las tres bebidas al Array
			bebidas.add(beb);
			bebidas.add(beb2);
			bebidas.add(beb3);

			// Array que contendrá 3 resultados a las pruebas
			ArrayList<Integer> pruebas = new ArrayList<Integer>();

			// Añado los numeros de errores en las pruebas, enel orden
			// respecto a l array de bebidas
			pruebas.add(2);
			pruebas.add(1);
			pruebas.add(0);

			// Recorro el array de bebidas y hago un test por cada una de ellas
			for (int i = 0; i < bebidas.size(); i++) {

				// Obtener las ConstrainViolation
				Set<ConstraintViolation<Bebida>> violations = validator.validate(bebidas.get(i));
				if (violations.size() > 0) {
					/* No ha pasado la valiadacion, iterar sobre los mensajes de validacion */
					for (ConstraintViolation<Bebida> violation : violations) {
						System.out.println(violation.getMessage());
						System.out.println(violation.getPropertyPath());
						System.out.println(violation.getInvalidValue());

					}
				} else {
					/* No tenemos ningun fallo, la Validacion es correcta */
					System.out.println("Sin fallos!");
				}

				// Controlo que me de dos errores
				assertTrue(violations.size() == pruebas.get(i));

				System.out.println(bebidas.get(i).toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
