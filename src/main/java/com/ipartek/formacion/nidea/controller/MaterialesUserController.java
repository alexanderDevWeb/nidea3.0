package com.ipartek.formacion.nidea.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.nidea.model.MaterialDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.nidea.ejemplos.Operable;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Servlet implementation class MaterialesUserController
 */
@WebServlet("/frontoffice/materiales")
public class MaterialesUserController extends HttpServlet implements Operable {
	private static final long serialVersionUID = 1L;

	// Definimos las vistas a las que puede ir
	private static final String VIEW_INDEX = "/frontoffice/materiales/index.jsp";
	private static final String VIEW_FORM = "/frontoffice/formUser.jsp";

	// public static final int OP_MOSTRAR_FORMULARIO = 1;
	// public static final int OP_BUSQUEDA = 2;
	// public static final int OP_ELIMINAR = 3;
	// public static final int OP_GUARDAR = 4;

	private RequestDispatcher dispatcher;
	private Alert alert;

	// DAOs para operaciones en la BD
	private MaterialDAO daoMaterial;

	// parametros comunes
	private String search; // Buscador por nombre
	private int op;

	// parametros del material
	private int id;
	private String nombre;
	private float precio;
	private Usuario user;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		daoMaterial = MaterialDAO.getInstance();

	}

	// Se ejecuta cuando paramos el servidor de aplicaciones
	@Override
	public void destroy() {

		super.destroy();
		daoMaterial = null;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * Unimos las peticiones doGet y doPost
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
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
			case OP_ELIMINAR:
				eliminar(request);
				break;
			case OP_BUSQUEDA: // Al filtrar por nombre
				buscar(request);
				break;
			case OP_GUARDAR: // Crear nuevo material
				guardar(request);
				break;
			default:
				listar(request);
				break;
			}

		} catch (Exception e) {

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
			// alert = new Alert("No ha introducido un nombre", Alert.TIPO_DANGER);
			// this.op = OP_MOSTRAR_FORMULARIO;
		}

		// El usuario aguardar es siempre el usuario de la sesión
		Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
		user = userSession;

	}

	private void listar(HttpServletRequest request) {

		Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");

		// Configuro los atributos
		request.setAttribute("materiales", daoMaterial.getAllByUser("", userSession));
		request.setAttribute("search", search);
		request.setAttribute("alert", alert);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);

	}

	private void guardar(HttpServletRequest request) {

		// Creo un material para configurarlo a continuación
		Material mat = new Material();

		// No es necesario
		// mat.setId(this.id);
		mat.setNombre(this.nombre);
		mat.setUser(this.user);

		// Implementar try and catch
		try {
			if (request.getParameter("precio") != null && request.getParameter("precio") != "") {
				precio = Float.parseFloat(request.getParameter("precio"));
				mat.setPrecio(this.precio);
			} else {
				precio = 0.00f;
			}

			if (this.id == -1) {
				// Configuro al alerta
				alert = new Alert("Creación de un nuevo material", Alert.TIPO_PRIMARY);

			} else {
				// Configuro al alerta
				alert = new Alert("Modificando el material con id: " + this.id, Alert.TIPO_WARNING);
				mat.setId(this.id);
			}

			if (mat.getNombre() != "" && mat.getPrecio() >= 0) {
				try {
					if (daoMaterial.save(mat)) { // He modificado el objeto por referencia en el save(mat)
						alert = new Alert("Material Guardado con id: " + mat.getId(), Alert.TIPO_PRIMARY);
					} else {
						alert = new Alert("Error Guardando, sentimos las molestias ", Alert.TIPO_WARNING);
					}
				} catch (MySQLIntegrityConstraintViolationException e) {

					e.printStackTrace();
					alert = new Alert("Error Guardando, EL MATERIAL YA EXISTE ", Alert.TIPO_DANGER);
				}

				request.setAttribute("material", mat);
				dispatcher = request.getRequestDispatcher(VIEW_FORM);

			} else {

				if (mat.getNombre() == "") {
					alert = new Alert("No ha introducido un nombre", Alert.TIPO_DANGER);
				} else if (mat.getPrecio() < 0) {
					alert = new Alert("El precio debe ser positivo", Alert.TIPO_DANGER);
				}

				request.setAttribute("material", mat);
				dispatcher = request.getRequestDispatcher(VIEW_FORM);
			}
		} catch (NumberFormatException n) {
			System.out.println("Precio no FLOAT");
			// Hay que devolver el material para que la vista no quede vacía
			request.setAttribute("material", mat);

			// Configuro la nueva alerta
			alert = new Alert("El precio ha de ser en formato numérico", Alert.TIPO_DANGER);

			// Configuro la vista a cargar
			dispatcher = request.getRequestDispatcher(VIEW_FORM);
		}

	}

	// ok
	private void buscar(HttpServletRequest request) {

		// Configuro los atributos
		request.setAttribute("materiales", daoMaterial.getAll(search));
		request.setAttribute("search", search);

		// Configuro al alerta
		alert = new Alert("Filtro por texto: " + search, Alert.TIPO_PRIMARY);

		// Preparo el dispatcher para el forward
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);

	}

	private void eliminar(HttpServletRequest request) {

		System.out.println("Eliminando.....");

		Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");

		if (daoMaterial.deleteChecked(id, userSession)) {
			alert = new Alert("Material Eliminado con id: " + id, Alert.TIPO_PRIMARY);
		} else {
			alert = new Alert("Error Eliminando, sentimos las molestias ", Alert.TIPO_WARNING);
		}

		listar(request);

	}

	private void mostrarFormulario(HttpServletRequest request) {

		// Indico la operación a realizar
		request.setAttribute("op", this.op);

		// Hago la consulta del material seleccionado mediante su id
		Material mat = new Material();

		if (this.id != -1) {
			mat = daoMaterial.getById(this.id);
			// Configuro al alerta
			alert = new Alert("Mostrando material con id: " + this.id, Alert.TIPO_PRIMARY);
		} else {
			// Configuro al alerta
			alert = new Alert("Creando nuevo material", Alert.TIPO_PRIMARY);
		}

		request.setAttribute("material", mat);

		// Preparo el dispatcher para el forward
		dispatcher = request.getRequestDispatcher(VIEW_FORM);

	}

}
