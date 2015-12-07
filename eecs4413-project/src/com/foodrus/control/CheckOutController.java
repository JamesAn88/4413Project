package com.foodrus.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodrus.bean.ShoppingCart;
import com.foodrus.bean.UserProfile;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.ViewPath;
import com.foodrus.util.ObjectFactory;
import com.foodrus.util.OrderType;
import com.foodrus.util.POHelper;

public class CheckOutController extends ControllerImpl {

	public CheckOutController(){
		super();
	}
	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// request.getAttribute(User)
		HttpSession session = request.getSession();
		UserProfile user = (UserProfile)session.getAttribute(ServletAttribute.USER_PROFILE);
		ShoppingCart cart = (ShoppingCart) session.getAttribute(ServletAttribute.CART);
		if (cart != null && !cart.getItems().isEmpty()){
			ObjectFactory fac = ObjectFactory.instanceOf();
			ServletContext context = request.getServletContext();
			try{
				OrderType order = fac.createOrder(cart, user, 
						context.getInitParameter(ServletAttribute.HST_CONTEXTPARAM), 
						context.getInitParameter(ServletAttribute.SHIPPING_COST_CONTEXTPARAM), 
						context.getInitParameter(ServletAttribute.WAIVE_CONTEXTPARAM),
						context.getInitParameter(ServletAttribute.REDUCED_SHIPPING_CONTEXTPARAM));
				//marshal order to file
				File poFile = POHelper.createPO(order, 
						request.getServletContext().getRealPath(ViewPath.PURCHASE_ORDERS_DIR));
				view.setDispatchType(View.FORWARD);
				view.setPath(Paths.get(ViewPath.PURCHASE_ORDERS_DIR, 
						poFile.getParentFile().getName(), 
						poFile.getName()).toString());
			} catch (Exception e){
				throw new ServletException(e);
			}
		}
		return view;
	}
}