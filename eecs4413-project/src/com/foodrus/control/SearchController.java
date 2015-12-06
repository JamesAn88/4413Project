package com.foodrus.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.dao.jdbp.ItemDao;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.ViewPath;

public class SearchController extends ControllerImpl {

	public SearchController() {
		super();
		view.setDispatchType(View.INCLUDE);
	}

	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("searchText");
		if(keyword != null && !keyword.trim().isEmpty()){
			try {
				List<?> result = (new ItemDao()).getItemsWithKeyWord(keyword);
				if(result.isEmpty()){
					request.setAttribute("notFound", "No Result were found for ["+
							request.getParameter("searchText")+"]");
				} else {
					request.setAttribute(ServletAttribute.ITEMS, result);
				}
				view.setPath(ViewPath.ITEMS);
				addTailCrumb(request, this.getRequstedUrl(request), "Result for ["+keyword+"]");
			} catch (NamingException | SQLException e) {
				throw new ServletException("Dao Exception was thrown", e);
			}
		}
		return view;
	}
}
