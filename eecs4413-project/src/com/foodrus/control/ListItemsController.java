package com.foodrus.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.dao.jdbp.ItemDao;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.ViewPath;

public class ListItemsController extends ControllerImpl {

	public ListItemsController(){
		super();
		view.setDispatchType(View.INCLUDE);
		view.setPath(ViewPath.HOME);
	}
	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String catId = request.getParameter("catId");
		String categoryName = request.getParameter("catName");
		categoryName = (categoryName == null) ? "Items" : categoryName;
		if(catId != null){
			try {
				request.setAttribute(ServletAttribute.ITEMS, new ItemDao().getItemsByCategory(catId));
				view.setPath(ViewPath.ITEMS);
			} catch (SQLException | NamingException e) {
				throw new ServletException("Could not retrieve Items from DAO", e);
			}
			addTailCrumb(request, this.getRequstedUrl(request), categoryName);
		} 
		return view;
	}
}
