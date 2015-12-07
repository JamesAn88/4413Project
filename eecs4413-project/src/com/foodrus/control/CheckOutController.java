package com.foodrus.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodrus.bean.ShoppingCart;
import com.foodrus.bean.UserProfile;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.ViewPath;
import com.foodrus.util.POHelper;

public class CheckOutController extends ControllerImpl {

	public CheckOutController(){
		super();
	}
	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		UserProfile user = (UserProfile)session.getAttribute(ServletAttribute.USER_PROFILE);
		ShoppingCart cart = (ShoppingCart) session.getAttribute(ServletAttribute.CART);
		String actionButton = request.getParameter("actionButton");
		if("Check out".equals(actionButton)){
			request.setAttribute("confirmOrder", Boolean.TRUE);
			view.setDispatchType(View.INCLUDE);
			view.setPath(ViewPath.CART);
		} else if("Confirm order".equals(actionButton)){
			this.marshalOrder(cart, user, request, context);
			request.setAttribute(ServletAttribute.PO_LIST, 
					POHelper.getUserPOs(user, 
							context.getRealPath(ViewPath.PURCHASE_ORDERS_DIR), 
							context.getContextPath()));
			view.setDispatchType(View.INCLUDE);
			view.setPath(ViewPath.MY_ACCOUNT);
		} else if("Cancel".equals(actionButton)){
			view.setDispatchType(View.INCLUDE);
			view.setPath(ViewPath.CART);
		} else if(request.getParameter("po_name") != null){
			String poFilePath = POHelper.getUserPO(user, request.getParameter("po_name"));
			request.setAttribute(ServletAttribute.PO, poFilePath);
			view.setDispatchType(View.FORWARD);
			view.setPath(ViewPath.ORDERS);
		}
		return view;
	}
	
	private void marshalOrder(ShoppingCart cart, UserProfile user, 
			HttpServletRequest request,ServletContext context) throws ServletException{
		if (cart != null && !cart.getItems().isEmpty()){
			try{
				//marshal order to file
				POHelper.createPO(request.getServletContext().
						getRealPath(ViewPath.PURCHASE_ORDERS_DIR), cart, user,
						context.getInitParameter(ServletAttribute.HST_CONTEXTPARAM), 
						context.getInitParameter(ServletAttribute.SHIPPING_COST_CONTEXTPARAM), 
						context.getInitParameter(ServletAttribute.WAIVE_CONTEXTPARAM),
						context.getInitParameter(ServletAttribute.REDUCED_SHIPPING_CONTEXTPARAM),
						context.getRealPath("/XML/style.xsl"));
			} catch (Exception e){
				throw new ServletException(e);
			}
		}
	}
}