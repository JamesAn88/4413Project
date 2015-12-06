package com.foodrus.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.bean.ShoppingCart;
import com.foodrus.bean.ShoppingItem;
import com.foodrus.bean.validate.ShoppingItemValidator;
import com.foodrus.bean.vo.Item;
import com.foodrus.dao.jdbp.ItemDao;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.ViewPath;

public class AddItemController extends ControllerImpl {
	
	public AddItemController(){
		super();
		view.setDispatchType(View.INCLUDE); 
	}
	
	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// *** get the item and validate it 
		Item item = null;
		String qty = request.getParameter("quantity");
		try {
			item  = new ItemDao().get(request.getParameter("itemNumber"));
		} catch (SQLException | NamingException e) {
			throw new ServletException("Could not get item with item number ["+
					request.getParameter("itemNumber")+"]", e);
		} 
		ShoppingItem shoppingItem = new ShoppingItem(item, qty);
		List<String> errors = (new ShoppingItemValidator()).validate(shoppingItem);
		
		// *** if valid then add to cart
		if(errors == null || errors.isEmpty()){
			ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute(ServletAttribute.CART);
			cart.addItem(shoppingItem);
			System.out.println(cart);
			view.setDispatchType(View.FORWARD);
			view.setPath("/action/showCart");
		// *** otherwise return the view to items with the validation errors	
		} else { 
			request.setAttribute(ServletAttribute.ERRORS, errors);
			request.setAttribute(ServletAttribute.ITEMS, Arrays.asList(item));
			request.setAttribute("qty", qty);
			view.setPath(ViewPath.ITEMS);
		}
		return view;
	}
}
