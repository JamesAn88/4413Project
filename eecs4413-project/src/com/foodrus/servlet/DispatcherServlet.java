package com.foodrus.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodrus.control.Controller;
import com.foodrus.util.Constants;
import com.foodrus.util.Constants.Resource;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.Url;
import com.foodrus.util.Constants.ViewPath;

/**
 * Dispatcher Servlet acts as the dispatcher of requests,
 * based on the requested URI, as follow:
 * <ul>
 * 	<li> <p>if no controller was found to handle the resource, then 
 *          the request is forwarded to the Home view.
 *  </li>    
 *  <li> <p>if controller exist then forward request to the view
 *          that the controller returned (if any).
 *          if the controller returns <code>null</code> then do nothing.
 * @author Uthman
 */
@WebServlet(urlPatterns={"/action/*", "/Action/*"})
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * instantiate a request dispatcher that dispatches
     * requests based on the URL
     */
    public DispatcherServlet() {
        super();
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}
    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}
    
    // *** process POST/GET requests
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		// *** parse the requested resource URI
		String url = request.getRequestURL().toString();
		HttpSession session = request.getSession();
		String uri = this.parseResources(url);
		System.out.println("Requested URI >>> " + uri);
		// *** get the Controller for that resource
		Controller controller = Resource.RESOURCE_MAP.get(uri);
		String target = (controller != null) ? controller.handleRequest(request, response) : ViewPath.HOME;
		// *** set home as default last visited
		if(target != null){
			session.setAttribute(ServletAttribute.LASTVISITED, url);
			session.setAttribute(ServletAttribute.LAST_QUERY_STRING, request.getQueryString());
			request.setAttribute(ServletAttribute.TARGET, target);
			request.getRequestDispatcher(ViewPath.DASH_BOARD).forward(request, response);
		} else {
			System.out.println("Controller ["+controller.getClass().getName()+"] returned [null] as View");
		}
	}

	// *** helper method that parse the requested resource from the requested URI
	private String parseResources(String uri) {
		String resource = null;
		if(uri != null){
			int lastIndex = uri.lastIndexOf(Url.SEPARATOR) + 1;
			resource = uri.substring(lastIndex).toLowerCase();
		}
		return resource;
	}
}
