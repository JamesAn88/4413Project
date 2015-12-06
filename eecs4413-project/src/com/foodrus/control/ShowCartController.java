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
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.ViewPath;

public class ShowCartController extends ControllerImpl {

	public ShowCartController() {
		super();
		view.setDispatchType(View.INCLUDE);
		view.setPath(ViewPath.CART);
	}

	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String actionButton = request.getParameter("actionButton");
		String newQty = request.getParameter("newQty");
		String itemNumber = request.getParameter("itemNumber");
		ShoppingItem updatedItem = new ShoppingItem (new Item(itemNumber), newQty);
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute(ServletAttribute.CART);
		if("Update".equals(actionButton)){
			List<String> errors = (new ShoppingItemValidator()).validate(updatedItem);
			if(errors == null || errors.isEmpty()){
				cart.updateItem(updatedItem);
			} else {
				request.setAttribute(ServletAttribute.ERRORS, errors);
			}
		}else if("Delete".equals(actionButton)){
			cart.removeItem(updatedItem);
		}
		addTailCrumb(request, this.getRequstedUrl(request), "Shopping Cart");
		return view;
	}

}
