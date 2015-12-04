package com.foodrus.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodrus.bean.ShoppingCart;
import com.foodrus.util.Constants;
import com.foodrus.util.Constants.ServletAttribute;

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
			System.out.println("From Checkout Controller, redirecting to: " + url);
			response.sendRedirect(url);
			return null;
		} 
		// user logged in
		ShoppingCart cart = (ShoppingCart) session.getAttribute(ServletAttribute.CART);
		if (cart == null || cart.getTotalItems() == 0){
			//no items
		} else {
			
		}
		return null;
	}

}
