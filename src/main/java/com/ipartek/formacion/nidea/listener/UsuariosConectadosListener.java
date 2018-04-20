package com.ipartek.formacion.nidea.listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Application Lifecycle Listener implementation class UsuariosConectadosListener
 *
 */
@WebListener
public class UsuariosConectadosListener implements HttpSessionListener, HttpSessionAttributeListener {

	// Este HashMap contendrá los usuarios conectados
	HashMap<Integer, Usuario> usuariosMap = new HashMap<Integer, Usuario>();

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("Sesión Creada");
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("Sesion destruida");

		// Recojo la sesión y añado al usuario con sesión abierta
		HttpSession session = se.getSession();

		// Si Existe algún usuario Publico
		if (session.getAttribute("usuario") != null) {
			// REcojo el usuario del que va a cerrarse la sesión
			Usuario userEnd = (Usuario) se.getSession().getAttribute("usuario");

			// Quito del hasmap el usuario que va a cerrar sesion
			usuariosMap.remove(userEnd.getId());

			// Escribo en el servlet context el hashmap nuevo
			ServletContext ctx = se.getSession().getServletContext();
			ctx.setAttribute("usuarios_conectados", usuariosMap);
		}
	}

	/**
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {

		System.out.println("Atributo añadido");

		// Si Existe algún usuario Publico
		if ("usuario".equals(event.getName())) {
			Usuario user = (Usuario) event.getValue();

			usuariosMap.put(user.getId(), user);

			// Comprobar que el atributo sea uPublic
			System.out.println("GetName(): " + event.getName());

			// Escribo en el servlet context el hashmap nuevo
			ServletContext ctx = event.getSession().getServletContext();
			ctx.setAttribute("usuarios_conectados", usuariosMap);
		}

	}

	/**
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Atributo removed");
	}

	/**
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
	}

}
