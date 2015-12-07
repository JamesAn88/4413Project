package com.foodrus.filter;

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
import javax.servlet.http.HttpSession;

import com.foodrus.servlet.DispatcherServlet;
import com.foodrus.util.Constants.Resource;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.ViewPath;

/**
 * Servlet Filter implementation class SecurityFilter
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR }, 
		servletNames = { "DispatcherServlet" })
public class SecurityFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SecurityFilter() {
        super();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		/* do nothing */
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		Boolean isLoggedIn = (Boolean)session.getAttribute(ServletAttribute.LOGGED_IN);
		String requestedUrl = DispatcherServlet.parseResources(httpRequest.getRequestURI());
		String forwardUrl = validateUser(requestedUrl, isLoggedIn);
		if(forwardUrl != null){
			request.getRequestDispatcher(forwardUrl).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		/* do nothing */
	}

	private String validateUser(String resource, Boolean isLoggedIn){
		String forwardUrl = null;
		if(Resource.CHECK_OUT.equals(resource)){
			if(isLoggedIn == null || !isLoggedIn){
				forwardUrl = ViewPath.LOGIN;
			}
		}
		return forwardUrl;
	}
}
