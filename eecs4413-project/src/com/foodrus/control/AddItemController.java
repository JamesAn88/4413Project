package com.foodrus.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.bean.ShoppingCart;
import com.foodrus.bean.ShoppingItem;
import com.foodrus.bean.validate.ShoppingItemValidator;
import com.foodrus.bean.vo.Item;
import com.foodrus.dao.jdbp.ItemDao;
import com.foodrus.util.Constants;
import com.foodrus.util.Constants.ServletAttribute;

public class AddItemController implements Controller {

	public AddItemController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String itemId = request.getParameter(Constants.ServletAttribute.CHOSEN_ITEM);
		String qntyString = request.getParameter(Constants.ServletAttribute.CHOSEN_ITEM_QNTY);
		if (itemId != null && qntyString != null){
			Item addedItem;
			try {
				addedItem = new ItemDao().get(itemId);
			} catch (Exception e) {
				throw new ServletException(e);
			} 
			ShoppingItem shoppingItem = new ShoppingItem(addedItem);
			shoppingItem.setQty(qntyString);
			ShoppingItemValidator validator = new ShoppingItemValidator();
			List<String> errors = validator.validate(shoppingItem);
			if(errors == null || errors.isEmpty()){
				ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute(ServletAttribute.CART);
				if(cart == null){
					cart = new ShoppingCart();
					request.getSession().setAttribute(ServletAttribute.CART, cart);
				}
				cart.addItem(shoppingItem);
				response.sendRedirect((String)request.getSession().getAttribute(Constants.ServletAttribute.LASTVISITED)+"?"+(String)request.getSession().getAttribute(Constants.ServletAttribute.LAST_QUERY_STRING));
			} else {
				request.setAttribute(ServletAttribute.ERRORS, errors);
				response.sendRedirect((String)request.getSession().getAttribute(Constants.ServletAttribute.LASTVISITED));
			}
		} else {
			// wtf to do here
		}
		return null;
	}
}
