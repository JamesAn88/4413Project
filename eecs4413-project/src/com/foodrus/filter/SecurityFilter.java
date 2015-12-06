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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		Boolean isLoggedIn = (Boolean)session.getAttribute(ServletAttribute.LOGGED_IN);
		if(Resource.CHECK_OUT.equals(DispatcherServlet.parseResources(httpRequest.getRequestURI()))){
			if(isLoggedIn == null || !isLoggedIn){
				request.getRequestDispatcher(ViewPath.LOGIN).forward(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
