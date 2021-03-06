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
		public static final String PO = "com.foodrus.util.po";
		public static final String CART = "com.foodrus.util.cart";
		public static final String USER_PROFILE = "com.foodrus.util.userProfile";
		public static final String LOGGED_IN = "com.foodrus.util.loggedIn";
		public static final String CATEGORIES = "com.foodrus.util.categories";
		public static final String ITEMS = "com.foodrus.util.items";
		public static final String SEARCH_RESULT = "com.foodrus.util.searchResult";
		public static final String ERRORS = "com.foodrus.util.errors";
		public static final String LASTVISITED = "com.foodrus.util.lasturl";
		public static final String LAST_QUERY_STRING = "com.foodrus.util.lastquery";
		public static final String PO_LIST ="com.foodrus.util.poList";
		public static final String USER = "user";
		public static final String HASH = "hash";
		public static final String BACK = "back";
		public static final String TRADESECRET = "11850539574803748954511465232603835743288401123108386029232010505137480652464235096705921628410148003499568874609717247628022206165767457311981401642693902";
		public static final String AUTHSERVER = "https://www.cse.yorku.ca/~cse21051/projectAuth/Auth.cgi?back=";
		public static final String HST_CONTEXTPARAM = "HST";
		public static final String REDUCED_SHIPPING_CONTEXTPARAM = "REDUCEDSHIPPING";
		public static final String SHIPPING_COST_CONTEXTPARAM = "SHIPPINGCOST";
		public static final String WAIVE_CONTEXTPARAM = "SHIPPINGWAIVE";
		public static final String CHOSEN_CAT = "chosencat";
		public static final String CHOSEN_ITEM = "chosenitem";
		public static final String CHOSEN_ITEM_QNTY = "chosenquantity";
		public static final String BREAD_CRUMBS = "breadCrumb";
		public static final String INTERVAL_TO_QUERY_CATEGORIES = "INTERVAL_TO_QUERY_CATEGORIES";
	}

	// *** ServletAttributes constants
	public static final class ViewPath {
		public static final String DASH_BOARD = "/Dashboard.jspx";
		public static final String CART = "/Cart.jspx";
		public static final String HOME = "/Home.jspx";
		public static final String CATEGORIES = "/Categories.jspx";
		public static final String CHECK_OUT = "/Checkout.jspx";
		public static final String SEARCH = "/Search.jspx";
		public static final String ITEMS = "/Items.jspx";
		public static final String ORDERS = "/Orders.jspx";
		public static final String PURCHASE_ORDERS_DIR = "/WEB-INF/purchaseOrders";
		public static final String IMAGES_DIR = "/res/img";
		public static final String LOGIN = "/action/login";
		public static final String MY_ACCOUNT = "/MyAccount.jspx";
	}
	
	// *** URL constants
	public static final class Url {
		public static final String SEPARATOR = "/";
	}

	// *** Resource constants
	public static final class Resource {
		public static final String CHECK_OUT = "checkout"; //used by filter
		private static final String LOGIN = "login";
		private static final String LOGOUT = "logout";
		private static final String HOME = "home";
		private static final String ADD_ITEM = "additem";
		private static final String SEARCH = "search";
		private static final String BROWSE_CATEGORIES = "browsecategories";
		private static final String LIST_ITEMS = "listitems";
		private static final String SHOW_CART = "showcart";
		private static final Map<String, Controller> res_map = new HashMap<>();
		static{
			res_map.put(LOGIN, new LoginController());
			res_map.put(HOME, new HomeController());
			res_map.put(ADD_ITEM, new AddItemController());
			res_map.put(CHECK_OUT, new CheckOutController());
			res_map.put(SEARCH, new SearchController());
			res_map.put(BROWSE_CATEGORIES, new BrowseCategoryController());
			res_map.put(LIST_ITEMS, new ListItemsController());
			res_map.put(SHOW_CART, new ShowCartController());
			res_map.put(LOGOUT, new LogOutController());
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
	
	//** PO Constants
	public static final class POConst{
		public static final String PREFIX = "po";
		public static final String SUFFIX = ".xml";
	}

}
