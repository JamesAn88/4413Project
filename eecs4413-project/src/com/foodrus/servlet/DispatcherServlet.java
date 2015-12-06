package com.foodrus.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.control.ControllerImpl;
import com.foodrus.control.View;
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
@WebServlet(urlPatterns={"/action/*", "/Action/*"},
		name="DispatcherServlet")
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
			HttpServletResponse response) throws ServletException, IOException {
		// *** parse the requested resource URI
		String resource = parseResources(request.getRequestURI());
		// *** get the Controller for that resource
		ControllerImpl controller = (ControllerImpl)Resource.RESOURCE_MAP.get(resource);
		View target = (controller != null) ? controller.handleRequest(request, response) : 
			new View(ViewPath.HOME, View.INCLUDE);
		// *** dispatch the request to target
		if(target != null){
			switch(target.getDispatchType()){
			  // include the target to the Dashboard 
			  case View.INCLUDE:
				request.setAttribute(ServletAttribute.TARGET, target.getPath());
				request.getRequestDispatcher(ViewPath.DASH_BOARD).forward(request, response);
			  break;
			  // forward the request
			  case View.FORWARD:
				request.getRequestDispatcher(target.getPath()).forward(request, response);
			  break;
			  // redirect the request
			  case View.REDIRECT:
				response.sendRedirect(target.getPath());
			  break;
			}
		}
	}

	// *** helper method that parse the requested resource from the requested URI
	public static String parseResources(String uri) {
		String resource = null;
		if(uri != null){
			int lastIndex = uri.lastIndexOf(Url.SEPARATOR) + 1;
			resource = uri.substring(lastIndex).toLowerCase();
		}
		return resource;
	}
}