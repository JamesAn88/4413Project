package com.foodrus.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.util.Constants.ViewPath;

public class HomeController extends ControllerImpl {

	public HomeController() {
		super();
		view.setDispatchType(View.INCLUDE);
		view.setPath(ViewPath.HOME);
	}

	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) {
		this.addTailCrumb(request, this.getRequstedUrl(request), "Home");
		return view;
	}
}
