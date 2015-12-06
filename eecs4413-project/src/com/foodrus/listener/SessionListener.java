package com.foodrus.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.foodrus.bean.CrumbDetail;
import com.foodrus.bean.ShoppingCart;
import com.foodrus.bean.UserProfile;
import com.foodrus.util.Constants.ServletAttribute;
import com.foodrus.util.Constants.Url;


/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

	@Override
    public void sessionCreated(HttpSessionEvent event)  {
		HttpSession session = event.getSession();
		
		// *** cart attribute
		session.setAttribute(ServletAttribute.CART, new ShoppingCart());
		
		// *** breadCrumbs attribute
		StringBuffer homePath = new StringBuffer(session.getServletContext().getContextPath());
		homePath.append(Url.SEPARATOR).append("action").append(Url.SEPARATOR).append("home");
		CrumbDetail breadCrumb = (new CrumbDetail("Home", homePath.toString()));
		session.setAttribute(ServletAttribute.BREAD_CRUMBS, breadCrumb);
		System.out.println(breadCrumb);
		
		// *** userProfile attribute
		session.setAttribute(ServletAttribute.LOGGED_IN, new UserProfile());
    }

    public void sessionDestroyed(HttpSessionEvent event)  { 
         // do nothing
    }
}
