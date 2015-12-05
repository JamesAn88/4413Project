package com.foodrus.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class POHelper {
	
	private static File getPOFile(File userDir){
		
		return null;
	}
	
	public static File getUserDir(String user, String poDir){
		File userDir = new File(poDir, user);
		if (!userDir.isDirectory()){
			userDir.mkdir();
		}
		return userDir;
	}
	
	public static boolean createPO(OrderType order, String poDir) throws Exception{
		boolean success = true;
		JAXBContext jc = JAXBContext.newInstance(order.getClass());
		Marshaller marsh = jc.createMarshaller();
		File userDir = getUserDir(order.getCustomer().getAccount(), poDir);
		File poFile = getPOFile(userDir);
		
		return success;
	}

}
