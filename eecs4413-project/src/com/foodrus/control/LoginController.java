package com.foodrus.control;

import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.foodrus.bean.CrumbDetail;
import com.foodrus.bean.UserProfile;
import com.foodrus.util.Constants.ServletAttribute;


public class LoginController extends ControllerImpl {

	@Override
	public View handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String user = request.getParameter(ServletAttribute.USER);
		view.setDispatchType(View.INCLUDE);
		if (user != null){
			//login attempt
			System.out.println("user = " + user);
			String hash = request.getParameter(ServletAttribute.HASH);
			String hashed;
			try {
				MessageDigest sha1Md = MessageDigest.getInstance("SHA1"); 
				byte[] digest = sha1Md.digest(user.concat(ServletAttribute.TRADESECRET).getBytes()); 
				hashed = DatatypeConverter.printHexBinary(digest);
				System.out.println("hash = " + hash);
				System.out.println("hashed = " + hashed);
				if (hash.equalsIgnoreCase(hashed)){
					//successful login
					UserProfile profile = (UserProfile)request.getSession().
							getAttribute(ServletAttribute.LOGGED_IN);
					//profile.setName here
					profile.setAccount(user);
					profile.setUserName(user);
					
					CrumbDetail crumb = (CrumbDetail)request.getSession().
							getAttribute(ServletAttribute.BREAD_CRUMBS);
					String lastVisited = crumb.getTail().getCrumbUrl();
					lastVisited = lastVisited.substring(request.getServletContext().getContextPath().length());
					view.setDispatchType(View.FORWARD);
					view.setPath(lastVisited);
				} else {
					throw new ServletException("User could not be authintcated");
				}
			} catch (Exception e){
				throw new ServletException(e);
			}
		} else {
			view.setDispatchType(View.REDIRECT);
			view.setPath(ServletAttribute.AUTHSERVER.concat(request.getRequestURL().toString()));
		}
		return view;
	}
}
