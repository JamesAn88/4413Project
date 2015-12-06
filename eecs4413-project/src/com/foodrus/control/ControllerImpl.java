package com.foodrus.control;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.bean.CrumbDetail;

public abstract class ControllerImpl implements Controller {

	protected View view;

	public ControllerImpl(){
		super();
		view = new View();
	}

	public abstract View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected View getView() {
		return view;
	}

	protected void setView(View view) {
		this.view = view;
	}
	
	public String getRequstedUrl(HttpServletRequest request){
		StringBuffer url = new StringBuffer(request.getRequestURI());
		url.append('?');
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String param = params.nextElement();
			url.append(param).append('=').append(request.getParameter(param)).append('&');
		}
		return url.toString();
	}
	
	protected void addTailCrumb(HttpServletRequest request, String url, String name){
		CrumbDetail crumb = (CrumbDetail)request.getSession().getAttribute("breadCrumb");
		CrumbDetail tail = new CrumbDetail(name, url); 
		crumb.setTail(tail);
		System.out.println();
	}
}
