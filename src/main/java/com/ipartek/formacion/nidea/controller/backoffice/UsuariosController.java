package com.ipartek.formacion.nidea.controller.backoffice;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.nidea.model.RolDAO;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class UsuariosController
 */
@WebServlet("/backoffice/usuarios")
public class UsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_INDEX = "/backoffice/usuarios/index.jsp";
	private static final String VIEW_FORM = "/backoffice/usuarios/formUser.jsp";

	public static final int OP_MOSTRAR_FORMULARIO = 1;
	public static final int OP_BUSQUEDA = 2;
	public static final int OP_ELIMINAR = 3;
	public static final int OP_GUARDAR = 4;

	private UsuarioDAO daoUsuario;
	private RolDAO daoRol;

	private Alert alert;

	private RequestDispatcher dispatcher;

	// Variables generales de operaciones
	private int op;
	private String search;

	// Variables del objeto Usuario
	private int id;
	private String nombre;
	private Rol rol;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		daoUsuario = UsuarioDAO.getInstance();
	}

	// Se ejecuta cuando paramos el servidor de aplicaciones
	@Override
	public void destroy() {

		super.destroy();
		daoUsuario = null;
	}

	// Para realizar operaciones antes o después de ejecutar doGet o do Post
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Antes de ejecutar doGet o doPost");

		// Llama a doGet o doPost
		super.service(req, resp);

		System.out.println("Después de ejecutar doGet o doPost");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// Reseteo el valor del alert
			alert = null;

			// RecogerParámetros
			recogerParametros(request);

			switch (op) {
			case OP_MOSTRAR_FORMULARIO: // Ver el detalle
				mostrarFormulario(request);
				break;
			// case OP_ELIMINAR:
			// eliminar(request);
			// break;
			// case OP_BUSQUEDA: // Al filtrar por nombre
			// buscar(request);
			// break;
			// case OP_GUARDAR: // Crear nuevo material
			// guardar(request);
			// break;*/
			default:
				listar(request);
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			request.setAttribute("alert", alert);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * Recogemos todos los posibles parámetros enviados
	 * 
	 * @param request
	 */
	private void recogerParametros(HttpServletRequest request) {

		System.out.println("Opción: " + request.getParameter("op"));

		if (request.getParameter("op") != null) {
			op = Integer.parseInt(request.getParameter("op"));
		} else {
			op = 0;
		}

		search = (request.getParameter("search") != null) ? request.getParameter("search").trim() : "";

		if (request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
		} else {
			id = -1;
		}

		System.out.println(request.getParameter("nombre"));
		if (request.getParameter("nombre") != null && request.getParameter("nombre") != "") {
			nombre = request.getParameter("nombre").trim();
		} else { // no ha introducido nombre
			nombre = "";
		}

		System.out.println("Rol: " + request.getParameter("idRol"));
		rol = new Rol();
		if (request.getParameter("idRol") != null) {
			// user = Integer.parseInt(request.getParameter("idUsuario"));
			rol = daoRol.getById(Integer.parseInt(request.getParameter("idRol")));
		}

	}

	private void mostrarFormulario(HttpServletRequest request) {

		// Indico la operación a realizar
		request.setAttribute("op", this.op);

		// Hago la consulta del material seleccionado mediante su id
		Usuario user = new Usuario();

		if (this.id != -1) {
			user = daoUsuario.getById(this.id);
			// Configuro al alerta
			alert = new Alert("Mostrando material con id: " + this.id, Alert.TIPO_PRIMARY);
		} else {
			// Configuro al alerta
			alert = new Alert("Creando nuevo material", Alert.TIPO_PRIMARY);
		}

		request.setAttribute("roles", daoRol.getAll(""));
		request.setAttribute("usuario", user);

		// Preparo el dispatcher para el forward
		dispatcher = request.getRequestDispatcher(VIEW_FORM);

	}

	private void listar(HttpServletRequest request) {

		// Configuro los atributos
		request.setAttribute("usuarios", daoUsuario.getAll(""));
		request.setAttribute("search", search);
		request.setAttribute("alert", alert);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);

	}

}
