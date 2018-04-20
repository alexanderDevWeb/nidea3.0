package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login-user")
public class LoginUserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// private String cont = "";
	private String view = "";

	private Alert alert = new Alert();

	// Guardo el id y usuario en el hashmap
	HashMap<Integer, String> usuariosMap = new HashMap<Integer, String>();

	// private static final int SESSION_EXPIRATION = 60 * 60;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("loginUser.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			int id = Integer.parseInt(request.getParameter("id"));
			String nombre = request.getParameter("nombre");

			// System.out.println(id);
			// System.out.println(nombre);

			// Creo un objeto Usuario con los valores
			// del usuario logueado
			Usuario user = new Usuario();
			user.setId(id);
			user.setNombre(nombre);

			// Guardar usuario en session
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(1);
			session.setAttribute("uPublic", user);

			// Comento el siguiente codigo porque utilizaremos un listener
			/*
			 * // Añado el nuevo usuario al hashmap
			 * usuariosMap.put(id, nombre);
			 * // Guardo el hashmap a nivel de aplicacion
			 * ServletContext context = request.getServletContext();
			 * context.setAttribute("usuarios_conectados", usuariosMap);
			 */

			// Voy a lista de materiales para los usuarios
			view = "views/materiales/index.jsp";

			alert = new Alert("Bienvenido " + nombre, Alert.TIPO_PRIMARY);

		} catch (Exception e) {
			e.printStackTrace();
			view = "loginUser.jsp";
			alert = new Alert();

			// cont = "login";

		} finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);

			// response.sendRedirect(cont);
		}

	}

}
