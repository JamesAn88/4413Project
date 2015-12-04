package com.foodrus.control;

import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodrus.bean.UserProfile;
import com.foodrus.util.Constants;


public class LoginController implements Controller {

	public LoginController() {
		super();
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String user = request.getParameter(Constants.ServletAttribute.USER);
		if (user != null){
			//login attempt
			System.out.println("user = " + user);
			String hash = request.getParameter(Constants.ServletAttribute.HASH);
			String hashed;
			try {
				hashed = (String)(javax.xml.bind.DatatypeConverter.printHexBinary(MessageDigest.getInstance("SHA1").digest((user+Constants.ServletAttribute.TRADESECRET).getBytes())));
				System.out.println("hash = " + hash);
				System.out.println("hashed = " + hashed);
				if (hash.equalsIgnoreCase(hashed)){
					//successful login
					UserProfile profile = new UserProfile();
					//profile.setName here
					profile.setAccount(user);
					request.getSession().setAttribute(Constants.ServletAttribute.LOGGED_IN, profile);
					
					String lastvisited = (String)request.getSession().getAttribute(Constants.ServletAttribute.LASTVISITED);
					if (lastvisited != null){
						System.out.println("redirecting to " + lastvisited);
						response.sendRedirect(lastvisited);
						return null;
					} else {
						return Constants.ViewPath.HOME;
					}
				} else {
					response.sendRedirect(Constants.ServletAttribute.AUTHSERVER+request.getRequestURL());
				}
			} catch (Exception e){
				throw new ServletException(e);
			}
		} else {
			try{
				response.sendRedirect(Constants.ServletAttribute.AUTHSERVER+request.getRequestURL());
				return null;
			} catch (Exception e){
				throw new ServletException(e);
			}
			
		}
		return null;
	}
}
