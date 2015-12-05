package com.foodrus.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.bean.vo.Item;
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
				request.setAttribute(Constants.ServletAttribute.ITEMS, new ItemDao().getItemsByCatid(category));
			} catch (Exception e){
				System.err.println("Could not retrieve Items from DAO: " + e.getMessage());
				throw new ServletException(e);
			}
		} else {
			try {
				List<Item> allItems= new ItemDao().getAll();
				request.setAttribute(Constants.ServletAttribute.ITEMS, allItems);
			} catch (Exception e) {
				System.err.println("Could not retrieve Items from DAO" + e.getMessage());
				throw new ServletException(e);
			}
		}
		
		
		
		return Constants.ViewPath.ITEMS;
	}

}
