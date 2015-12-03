package com.foodrus.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckOutController implements Controller {

	public CheckOutController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// request.getAttribute(User)
		HttpSession session = request.getSession();
		
		return null;
	}

}
