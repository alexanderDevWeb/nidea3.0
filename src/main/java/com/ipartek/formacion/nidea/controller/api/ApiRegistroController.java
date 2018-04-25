package com.ipartek.formacion.nidea.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class ApiUsuarioController
 */
@WebServlet("/api/registro")
public class ApiRegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDAO dao = UsuarioDAO.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		// Recoger parametro nombre.
		// Comprobar si ya existe el usuario en la BD
		// Devolver true or false
		String nombre = request.getParameter("nombre");
		if (nombre == null) {
			nombre = "desconocido";
		}

		ArrayList<Usuario> usuarios = dao.checkNameRegistro(nombre);
		// En vez de like ponerlo = para que sea exacto

		if (usuarios.size() == 0) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			out.print("Usuario Libre");
		} else {
			// Por defecto siempre retorna 200 = SC_OK
			out.print("Usuario Existente");
		}
		out.flush();
	}

}
