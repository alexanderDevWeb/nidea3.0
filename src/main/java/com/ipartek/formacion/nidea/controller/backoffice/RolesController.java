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
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Rol;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Servlet implementation class RolesController
 */
@WebServlet("/backoffice/roles")
public class RolesController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Definimos las vistas a las que puede ir
	private static final String VIEW_INDEX = "/backoffice/roles/index.jsp";
	private static final String VIEW_FORM = "/backoffice/roles/formRol.jsp";

	public static final int OP_MOSTRAR_FORMULARIO = 1;
	public static final int OP_BUSQUEDA = 2;
	public static final int OP_ELIMINAR = 3;
	public static final int OP_GUARDAR = 4;

	private RequestDispatcher dispatcher;
	private Alert alert;

	// Creamos el atributo RolDAO y el método init()
	// para que solo se cree el dao en la primera llamada al servlet,
	// no cada vez que se llamaba al doGet, así quedará creado
	private RolDAO dao;

	// parametros comunes
	private String search; // Buscador por nombre
	private int op;

	// parametros del rol
	private int id;
	private String nombre;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = RolDAO.getInstance();
	}

	// Se ejecuta cuando paramos el servidor de aplicaciones
	@Override
	public void destroy() {
		super.destroy();
		dao = null;
	}

	// Para realizar operaciones antes o después de ejecutar doGet o do Post
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("Antes de ejecutar doGet o doPost");

		// Llama a doGet o doPost
		super.service(req, resp);

		// System.out.println("Después de ejecutar doGet o doPost");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
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
			case OP_BUSQUEDA: // Al filtrar por nombre
				buscar(request);
				break;
			case OP_ELIMINAR:
				eliminar(request);
				break;
			case OP_GUARDAR: // Crear nuevo rol
				guardar(request);
				break;
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
		// TODO Auto-generated method stub
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

		System.out.println("Nuevo rol: " + request.getParameter("nombre"));
		if (request.getParameter("nombre") != null && request.getParameter("nombre") != "") {
			nombre = request.getParameter("nombre").trim();
		} else { // no ha introducido nombre
			nombre = "";
			// alert = new Alert("No ha introducido un nombre", Alert.TIPO_DANGER);
			// this.op = OP_MOSTRAR_FORMULARIO;
		}

	}

	// ok
	private void listar(HttpServletRequest request) {

		// Configuro los atributos
		request.setAttribute("roles", dao.getAll(""));
		request.setAttribute("search", search);
		request.setAttribute("alert", alert);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);

	}

	private void guardar(HttpServletRequest request) {

		// Creo un rol para configurarlo a continuación
		Rol rol = new Rol();
		rol.setId(this.id);
		rol.setNombre(this.nombre);

		// Implementar try and catch
		try {

			if (this.id == -1) {
				// Configuro al alerta
				alert = new Alert("Creación de un nuevo rol", Alert.TIPO_PRIMARY);

			} else {
				// Configuro al alerta
				alert = new Alert("Modificando el rol con id: " + this.id, Alert.TIPO_WARNING);
				rol.setId(this.id);
			}

			if (rol.getNombre() != "") {
				try {
					if (dao.save(rol)) { // He modificado el objeto por referencia en el save(rol)
						alert = new Alert("Rol Guardado con id: " + rol.getId(), Alert.TIPO_PRIMARY);
					} else {
						alert = new Alert("Error Guardando, sentimos las molestias ", Alert.TIPO_WARNING);
					}
				} catch (MySQLIntegrityConstraintViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					alert = new Alert("Error Guardando, EL ROL YA EXISTE ", Alert.TIPO_DANGER);
				}

				// this.listar(request);
				request.setAttribute("roles", dao.getAll(""));
				request.setAttribute("rol", rol);
				dispatcher = request.getRequestDispatcher(VIEW_FORM);

			} else {
				alert = new Alert("No ha introducido un nombre", Alert.TIPO_DANGER);
				request.setAttribute("roles", dao.getAll(""));
				request.setAttribute("rol", rol);
				dispatcher = request.getRequestDispatcher(VIEW_FORM);
			}
		} catch (Exception n) {

			// Hay que volver a enviar la información
			// para que la vista no quede vacía
			request.setAttribute("roles", dao.getAll(""));
			request.setAttribute("rol", rol);

			// Configuro la nueva alerta
			alert = new Alert("Ha saltado la excepción", Alert.TIPO_DANGER);

			// Configuro la vista a cargar
			dispatcher = request.getRequestDispatcher(VIEW_FORM);
		}

	}

	// ok
	private void buscar(HttpServletRequest request) {

		// Configuro los atributos
		request.setAttribute("roles", dao.getAll(search));
		request.setAttribute("search", search);

		// Configuro al alerta
		alert = new Alert("Filtro por texto: " + search, Alert.TIPO_PRIMARY);

		// Preparo el dispatcher para el forward
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);

	}

	private void eliminar(HttpServletRequest request) {
		// TODO Auto-generated method stub
		System.out.println("Eliminando.....");

		if (dao.delete(id)) {
			alert = new Alert("Rol Eliminado con id: " + id, Alert.TIPO_PRIMARY);
		} else {
			alert = new Alert("Error Eliminando, sentimos las molestias ", Alert.TIPO_WARNING);
		}

		listar(request);

	}

	private void mostrarFormulario(HttpServletRequest request) {

		// Indico la operación a realizar
		request.setAttribute("op", this.op);

		// Hago la consulta del rol seleccionado mediante su id
		Rol rol = new Rol();

		if (this.id != -1) {
			rol = dao.getById(this.id);
			// Configuro al alerta
			alert = new Alert("Rol con id: " + this.id, Alert.TIPO_PRIMARY);
		} else {
			// Configuro al alerta
			alert = new Alert("Creando nuevo rol", Alert.TIPO_PRIMARY);
		}

		request.setAttribute("roles", dao.getAll(""));
		request.setAttribute("rol", rol);

		// Preparo el dispatcher para el forward
		dispatcher = request.getRequestDispatcher(VIEW_FORM);

	}

}
