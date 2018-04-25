package com.ipartek.formacion.nidea.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class ApiUsuarioController
 */
@WebServlet("/api/usuario")
public class ApiUsuarioController extends HttpServlet {
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

		// Recoger parametros
		String nombre = request.getParameter("nombre");
		if (nombre == null) {
			nombre = "desconocido";
		}

		ArrayList<Usuario> usuarios = dao.getAllByName(nombre);

		if (usuarios.size() == 0) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} else {
			// Por defecto siempre retorna 200 = SC_OK
		}

		// Covierte el array de usuarios a JSON
		String gson = new Gson().toJson(usuarios);

		// Envió la información
		out.print(gson);
		out.flush();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

}
