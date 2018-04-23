package com.ipartek.formacion.nidea.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// private String cont = "";
	private String view = "";

	private Alert alert = new Alert();

	// Se utilizaban al principio al hardcodear el login
	// private static final String USER = "admin";
	// private static final String PASS = "admin";

	private static final String BACKOFFICE = "backoffice/index.jsp";
	private static final String FRONTOFFICE = "views/materiales/index.jsp";
	private static final String LOGIN = "login.jsp";

	// private static final int SESSION_EXPIRATION = 60 * 60;

	// Creamos el atributo UsuarioDAO y el método init()
	// para que solo se cree el dao en la primera llamada al servlet,
	// no cada vez que se llamaba al doGet, así quedará creado
	private UsuarioDAO daoUser;

	// Creamos el atributo MaterialDAO y el método init()
	// para que solo se cree el dao en la primera llamada al servlet,
	// no cada vez que se llamaba al doGet, así quedará creado
	// private MaterialDAO daoMaterial;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		daoUser = UsuarioDAO.getInstance();
		// daoMaterial = MaterialDAO.getInstance();
	}

	// Se ejecuta cuando paramos el servidor de aplicaciones
	@Override
	public void destroy() {

		super.destroy();
		daoUser = null;
		// daoMaterial = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("login.jsp").forward(request, response);

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

			// Consulto si existe el usuario introducido
			Usuario user = daoUser.check(usuario, password);

			if (user != null) { // Si existe el user compruebo el rol para redirigirle
				// Guardar usuario en session
				HttpSession session = request.getSession();
				session.setAttribute("usuario", user);
				// session.setMaxInactiveInterval(SESSION_EXPIRATION);

				// Recojo todos los usuarios en contexto de palicación
				// desde que se enciende el servidor hasta que se apaga
				// ServletContext context = request.getServletContext();
				// HashMap<Integer, String> usuariosMap = (HashMap<Integer, String>) context
				// .getAttribute("usuarios_conectados");

				// request.setAttribute("usuarios_conectados", usuariosMap);

				if (user.getRol().getId() == 1) { // Si es admin le envio al backoffice

					view = BACKOFFICE;
					alert = new Alert("Ongi Etorri Administrador!", Alert.TIPO_PRIMARY);

				} else if (user.getRol().getId() == 2) { // Si es user normal
					// Voy a lista de materiales para los usuarios
					view = FRONTOFFICE;
					alert = new Alert("Ongi Etorri Usuario!", Alert.TIPO_PRIMARY);
				}
			} else {

				view = LOGIN;
				alert = new Alert("Credenciales incorrectas, prueba de nuevo");
			}

		} catch (Exception e) {
			e.printStackTrace();
			view = LOGIN;
			alert = new Alert();

		} finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}
	}
}
