package com.foodrus.control;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
				this.resolvePictures(request.getServletContext().getRealPath("/res/img"));
			} catch (SQLException | NamingException e) {
				throw new ServletException("Could not get AllCategories",e);
			}
		}
		addTailCrumb(request, this.getRequstedUrl(request), "Browse Categpries");
		request.setAttribute(ServletAttribute.CATEGORIES, cats);
		return view;
	}
	
	// *** helper method to write the pictures of Categories to local storage
	// *** then release the picture and maintin the path
	private void resolvePictures(String outputFolder) throws IOException {
		for(Category cat : cats){
			Path path = Paths.get(outputFolder, String.valueOf(cat.getId()));
			OutputStream out = new BufferedOutputStream(new FileOutputStream(path.toFile()));
			out.write(cat.getPicture());
			out.close();
			// release the picture bytes to save memory
			cat.setPicture(null); 
		}
	}
}