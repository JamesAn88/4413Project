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

public class CheckOutController extends ControllerImpl {

	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// request.getAttribute(User)
		HttpSession session = request.getSession();
		UserProfile user = (UserProfile)session.getAttribute(Constants.ServletAttribute.LOGGED_IN);
		ShoppingCart cart = (ShoppingCart) session.getAttribute(ServletAttribute.CART);
		if (cart != null && !cart.getItems().isEmpty()){
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
