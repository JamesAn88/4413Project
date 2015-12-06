package com.foodrus.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	
	/**
	 * This method will be called by a servletDispatcher, 
	 * for all requests to the controller.
	 * @param request the request to be handled
	 * @param response
	 * @return the target view for the request to be forwarded
	 */
	View handleRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException;
}
