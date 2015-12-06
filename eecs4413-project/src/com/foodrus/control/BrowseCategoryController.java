package com.foodrus.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.bean.vo.Category;
import com.foodrus.dao.jdbp.CategoryDao;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.ViewPath;
import com.foodrus.util.IOHelper;

public class BrowseCategoryController extends ControllerImpl{
	
	private static long lastDatabaseUpdate = 0;
	private static List<Category> cats;
	
	public BrowseCategoryController (){
		super();
		view.setPath(ViewPath.CATEGORIES);
		view.setDispatchType(View.INCLUDE);
	}
	
	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String param = request.getServletContext().
				getInitParameter(ServletAttribute.INTERVAL_TO_QUERY_CATEGORIES);
		param = (param == null) ? "-1" : param;
		long interval = TimeUnit.SECONDS.toMillis(Long.valueOf(param));
		long timeElapsed = System.currentTimeMillis() - lastDatabaseUpdate;
		if(cats == null || timeElapsed > interval){
			try {
				cats = (new CategoryDao()).getAll();
				lastDatabaseUpdate = System.currentTimeMillis();
				IOHelper.resolvePictures(request.getServletContext().getRealPath(ViewPath.IMAGES_DIR), cats);
			} catch (SQLException | NamingException e) {
				throw new ServletException("Could not get AllCategories",e);
			}
		}
		addTailCrumb(request, this.getRequstedUrl(request), "Browse Categpries");
		request.setAttribute(ServletAttribute.CATEGORIES, cats);
		return view;
	}
}