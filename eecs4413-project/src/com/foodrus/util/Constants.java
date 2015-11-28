package com.foodrus.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.foodrus.control.*;

public class Constants {
	
	// *** utility class
	private Constants() {
	}

	// *** ServletAttributes constants
	public static final class ServletAttribute {
		public static final String CONTEXT_PATH = "contextPath"; // ServletContext Attribute
		
		public static final String TARGET = "com.foodrus.util.target";
		public static final String CART = "com.foodrus.util.cart";
		public static final String LOGGED_IN = "com.foodrus.util.loggedIn";
		public static final String LOGGED_OUT = "com.foodrus.util.loggedOut";
		public static final String CATEGORIES = "com.foodrus.util.categories";
		public static final String ITEMS = "com.foodrus.util.items";
		public static final String SEARCH_RESULT = "com.foodrus.util.searchResult";
		public static final String ERRORS = "com.foodrus.util.errors";
	}

	// *** ServletAttributes constants
	public static final class ViewPath {
		public static final String DASH_BOARD = "/Dashboard.jspx";
		public static final String CART = "/Cart.jspx";
		public static final String HOME = "/Home.jspx";
		public static final String CATEGORIES = "/Categories.jspx";
		public static final String CHECK_OUT = "/Checkout.jspx";
		public static final String SEARCH = "/Search.jspx";
	}
	
	// *** URL constants
	public static final class Url {
		public static final String SEPARATOR = "/";
	}

	// *** Resource constants
	public static final class Resource {
		private static final String LOGIN = "login";
		private static final String HOME = "home";
		private static final String ADD_ITEM = "additem";
		private static final String REMOVE_ITEM = "removeitem";
		private static final String CHECK_OUT = "checkout";
		private static final String SEARCH = "search";
		private static final String BROWSE_CATEGORIES = "browsecategories";
		private static final String LIST_ITEMS = "listitems";
		private static final String SHOW_CART = "showcart";
		private static final Map<String, Controller> res_map = new HashMap<>();
		static{
			res_map.put(LOGIN, new LoginController());
			res_map.put(HOME, new HomeController());
			res_map.put(ADD_ITEM, new AddItemController());
			res_map.put(REMOVE_ITEM, new RemoveItemController());
			res_map.put(CHECK_OUT, new CheckOutController());
			res_map.put(SEARCH, new SearchController());
			res_map.put(BROWSE_CATEGORIES, new BrowseCategoryController());
			res_map.put(LIST_ITEMS, new ListItemsController());
			res_map.put(SHOW_CART, new ShowCartController());
		}
		
		public static final Map<String, Controller> RESOURCE_MAP = Collections.unmodifiableMap(res_map);
	}
	
	// *** HTTP methods names
	public static final class HttpMethod{
		public static final String GET = "GET";
		public static final String POST = "POST";
	}
	
	// ** DAO constants
	public static final class DAO{
		public static final String JNDI_NAME = "java:/comp/env/jdbc/EECS";
	}
}
