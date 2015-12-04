package com.foodrus.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodrus.util.Constants;

public class CheckOutController implements Controller {

	public CheckOutController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// request.getAttribute(User)
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute(Constants.ServletAttribute.LOGGED_IN);
		if (user == null){
			//not logged in
			session.setAttribute(Constants.ServletAttribute.LASTVISITED, request.getRequestURL().toString()); //hack
			//response.sendRedirect(request.getServerName()+request.getServerPort()+);
			//redirect to login
			String url = request.getRequestURL().toString();
			url = url.substring(0, url.indexOf(request.getPathInfo()));
			url=url+Constants.Url.SEPARATOR+Constants.Resource.LOGIN;
			response.sendRedirect(url);
			return null;
		} else {
			
		}
		
		return null;
	}

}
