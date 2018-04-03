package com.ipartek.formacion.nidea.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class BackofficeFilter
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.ERROR }, description = "Dejar pasar  solo a los usuario logueados", urlPatterns = {
				"/backoffice/*" })
public class BackofficeFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		System.out.println("BackOfficeFilter Destroy");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();
		if (null != session.getAttribute("usuario")) {
			chain.doFilter(request, response);
		} else {
			informacionPeticion(req);
			res.sendRedirect(req.getContextPath() + "/login");
		}

	}

	// Mostramos por pantalla toda la información del usuario
	private void informacionPeticion(HttpServletRequest req) {
		System.out.println("********** ACCESO NO PERMITIDO **********");

		System.out.println("IP: " + req.getLocalAddr());
		System.out.println("Idioma: " + req.getLocale());
		System.out.println("URI: " + req.getRequestURI());
		System.out.println("URI: " + req.getRemotePort());

		System.out.println("Navegador: " + req.getHeader("user-agent"));

		System.out.println("*****************************************");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("BackOfficeFilter init");
	}

}
