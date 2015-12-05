package com.foodrus.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.dao.jdbp.ItemDao;
import com.foodrus.util.Constants;


public class ListItemsController implements Controller {

	public ListItemsController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter(Constants.ServletAttribute.CATEGORIES);
		if (category != null){
			try{
				request.setAttribute("items", new ItemDao().getItemsByCatid(category));
			} catch (Exception e){
				System.err.println("Could not retrieve Items from DAO: " + e.getMessage());
				throw new ServletException(e);
			}
		}
		/**
		try {
			request.setAttribute("items", new ItemDao().getAll());
		} catch (SQLException | NamingException e) {
			System.err.println("Could not retrieve Items from DAO");
			throw new ServletException(e);
		}
		**/
		return Constants.ViewPath.HOME;
	}

}
