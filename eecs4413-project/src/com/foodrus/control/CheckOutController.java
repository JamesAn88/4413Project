package com.foodrus.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodrus.bean.ShoppingCart;
import com.foodrus.bean.UserProfile;
import com.foodrus.util.Constants;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.ObjectFactory;
import com.foodrus.util.OrderType;

public class CheckOutController implements Controller {

	public CheckOutController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// request.getAttribute(User)
		HttpSession session = request.getSession();
		UserProfile user = (UserProfile)session.getAttribute(Constants.ServletAttribute.LOGGED_IN);
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
			ObjectFactory fac = ObjectFactory.instanceOf();
			ServletContext context = request.getServletContext();
			OrderType order = null;
			try{
				 order = fac.createOrder(cart, user, 
						context.getInitParameter(Constants.ServletAttribute.HST_CONTEXTPARAM), 
						context.getInitParameter(Constants.ServletAttribute.SHIPPING_COST_CONTEXTPARAM), 
						context.getInitParameter(Constants.ServletAttribute.WAIVE_CONTEXTPARAM),
						context.getInitParameter(Constants.ServletAttribute.REDUCED_SHIPPING_CONTEXTPARAM));
			} catch (Exception e){
				//Not recommended, a lot of work has been done in the above try
				throw new ServletException(e);
			}
			//marshall order to file
			
		}
		return null;
	}

}
