package com.foodrus.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutController extends ControllerImpl {
	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();;
		view.setPath(request.getServletContext().getContextPath());
		view.setDispatchType(View.REDIRECT);
		return view;
	}
}
