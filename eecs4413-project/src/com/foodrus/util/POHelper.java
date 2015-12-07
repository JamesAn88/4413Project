package com.foodrus.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.foodrus.bean.ShoppingCart;
import com.foodrus.bean.UserProfile;
import com.foodrus.util.Constants.POConst;
import com.foodrus.util.Constants.ViewPath;

public class POHelper {
	
	public static final String XML_HEADER_FORMAT = "<?xml-stylesheet type='text/xsl' href='%s'?>";
	
	private static File getPOFile(File userDir) throws IOException{
		File file = null;
		try{
			String filePath = POConst.PREFIX;
			int count = userDir.list().length;
			if (count < 10){
				filePath = filePath + "0";
			}
			filePath = filePath + Integer.toString(count++);
			filePath = filePath + POConst.SUFFIX;
			file = Files.createFile(userDir.toPath().resolve(filePath)).toFile();
		} catch(Exception ex) {
			throw new IOException("Could not create PO file in user dir ["+userDir+"]", ex);
		}
		return file;
	}
	
	public static File getUserDir(String user, String poDir) throws IOException{
		File file = null;
		try{
			if(!Files.exists(Paths.get(poDir, user))){
				file = Files.createDirectory(Paths.get(poDir, user)).toFile();
			}else{
				file = Paths.get(poDir, user).toFile();
			}
		} catch (Exception ex){
			throw new IOException("Could not create user dir for user ["+user+"], poDir["+poDir+"]", ex);
		}
		return file; 
	}
	
	public static File createPO(String poDir, ShoppingCart cart, UserProfile user, String hst,
			String shippingCost, String waive, String reduced, String styleSheetPath) throws IOException{
		File poFile = null;
		try {
			File userDir = getUserDir(user.getAccount(), poDir);
			poFile = getPOFile(userDir);
			OrderType order = ObjectFactory.instanceOf().createOrder(cart, user, hst,
					shippingCost, waive, reduced, userDir.list().length);
			JAXBContext jc = JAXBContext.newInstance(order.getClass());
			Marshaller marsh = jc.createMarshaller();
			marsh.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
					String.format(XML_HEADER_FORMAT, styleSheetPath));
			marsh.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marsh.marshal(order, poFile);
		} catch (Exception e) {
			throw new IOException("Could not marshall file for user ["+user+"]"
					+ " and poDir ["+poDir+"]", e);
		}
		return poFile;
	}

	public static Map<String, String> getUserPOs(UserProfile user, String localPath, String contextPath) {
		Map<String, String> map = new HashMap<>();
		if(user != null){
			File[] fileList = Paths.get(localPath, 
					user.getAccount()).toFile().listFiles();
			if(fileList != null){
				for(File f : fileList){
					String poName = f.getName().replaceFirst("[.][^.]+$", "");
					String poPath = Paths.get(contextPath, "action/checkOut?po_name="+f.getName()).toString();
					map.put(poName, poPath);
				}
			}
		}
		return map; 
	}
	
	public static String getUserPO(UserProfile user, String poName) {
		String poPath = null;
		if(user != null && poName != null){
			poPath = Paths.get(ViewPath.PURCHASE_ORDERS_DIR, user.getAccount(), poName).toString();
		}
		return poPath; 
	}
}
