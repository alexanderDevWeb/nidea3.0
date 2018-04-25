package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.formacion.nidea.pojo.form.RegistroForm;

/**
 * Servlet implementation class RegistroController
 */
@WebServlet("/registro")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// private String cont = "";
	private String view = "";

	private Alert alert = new Alert();

	private static final String LOGIN = "login.jsp";
	private static final String REGISTRO = "registro.jsp";

	// private static final int SESSION_EXPIRATION = 60 * 60;

	// Creamos el atributo UsuarioDAO y el método init()
	// para que solo se cree el dao en la primera llamada al servlet,
	// no cada vez que se llamaba al doGet, así quedará creado
	private UsuarioDAO daoUser;

	// Factory para validaciones
	ValidatorFactory factory;
	Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoUser = UsuarioDAO.getInstance();

		// Crear Factoria y Validador
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	// Se ejecuta cuando paramos el servidor de aplicaciones
	@Override
	public void destroy() {
		super.destroy();
		daoUser = null;
		factory = null;
		validator = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("registro.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Recojo los parámetros envíados desde el login
			String usuario = request.getParameter("usuario");
			String password = request.getParameter("password");
			String cPassword = request.getParameter("cPassword");

			// Hay validaciones de javax.validation
			// Creo una clase para estas validaciones
			RegistroForm form = new RegistroForm(usuario, password, cPassword);

			// Vuelvo a comprobar que las contraseñas sean iguales
			if (!password.equals(cPassword)) {
				view = REGISTRO;
				alert = new Alert("Las contraseñas no coinciden. No me hackeeees!", Alert.TIPO_DANGER);

			} else { // Creo un objeto Usuario para guardarlo

				// Colleción con las violaciones de las validaciones
				Set<ConstraintViolation<RegistroForm>> violations = validator.validate(form);

				Usuario user = new Usuario();
				user.setNombre(usuario);
				user.setPassword(password);

				if (violations.size() == 0) { // Ha pasado todas las validaciones

					// Guardo el user mediante el dao
					daoUser.registrar(user);

					view = LOGIN;
					alert = new Alert("Usuario <i><strong>" + usuario + "</strong></i> registrado! Logueate",
							Alert.TIPO_PRIMARY);

				} else { // Hay violaciones en las validaciones
					String mensajes = "";
					for (ConstraintViolation<RegistroForm> violation : violations) {
						mensajes += violation.getPropertyPath() + ": " + violation.getMessage() + "<br>";

						request.setAttribute("user", user);
						alert = new Alert(mensajes, Alert.TIPO_DANGER);
						view = REGISTRO;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			view = REGISTRO;
			alert = new Alert();
		} finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}
	}

}
