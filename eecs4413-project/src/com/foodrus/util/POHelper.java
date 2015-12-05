package com.foodrus.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class POHelper {
	
	private static File getPOFile(File userDir){
		String filePath = Constants.POConst.PREFIX;
		filePath = userDir.getName();
		int count = userDir.list().length;
		if (count < 10){
			filePath = filePath + "0";
		}
		filePath = filePath + Integer.toString(count);
		filePath = filePath + Constants.POConst.SUFFIX;
		return new File(userDir, filePath);
	}
	
	public static File getUserDir(String user, String poDir){
		File userDir = new File(poDir, user);
		if (!userDir.isDirectory()){
			userDir.mkdir();
		}
		return userDir;
	}
	
	public static boolean createPO(OrderType order, String poDir) throws Exception{
		boolean success = false;
		JAXBContext jc = JAXBContext.newInstance(order.getClass());
		Marshaller marsh = jc.createMarshaller();
		File userDir = getUserDir(order.getCustomer().getAccount(), poDir);
		File poFile = getPOFile(userDir);
		marsh.marshal(order, poFile);
		if (poFile.exists() && poFile.isFile()){
			success = true;
		}
		return success;
	}

}
